package com.sdi.presentation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import alb.util.date.DateUtil;

import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;

@ManagedBean(name="tasks")
@ViewScoped
public class TasksBean {
	
	private List<Task> tasks;
	
	//TODO: mirar si es mejor crear otra clase?
	private Map<String, String> categories;
	private boolean showFinished;
	private User user;
	private Task selected;
	private String actual;
	
	@ManagedProperty(value = "#{createTask}")
	private CreateTaskBean createTaskBean;
	
	@ManagedProperty(value = "#{editTask}")
	private EditTaskBean editTaskBean;

	public EditTaskBean getEditTaskBean() {
		return editTaskBean;
	}

	public void setEditTaskBean(EditTaskBean editTaskBean) {
		this.editTaskBean = editTaskBean;
	}

	public CreateTaskBean getCreateTaskBean() {
		return createTaskBean;
	}

	public void setCreateTaskBean(CreateTaskBean createTaskBean) {
		this.createTaskBean = createTaskBean;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public boolean isShowFinished() {
		return showFinished;
	}

	public void setShowFinished(boolean showFinished) {
		this.showFinished = showFinished;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getSelected() {
		return selected;
	}

	public void setSelected(Task selected) {
		this.selected = selected;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public Map<String, String> getCategories() {
		return categories;
	}

	public void setCategories(Map<String, String> categories) {
		this.categories = categories;
	}

	
	@PostConstruct
	public void init(){
		user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("logedUser"));
		
		categories = new HashMap<>();
		
		//TODO: esto estaría bien?
		listarInbox();
		listarCategorias();
		
	}
	

	public String listarInbox(){
		TaskService tService;
		actual = "inbox";
		try{
			tService = Services.getTaskService();
			
			tasks = tService.findInboxTasksByUserId(user.getId());
			
			if(showFinished){
				tasks.addAll(tService.findFinishedInboxTasksByUserId(user.getId()));
			}
			
			return "listadoTareas";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		
		
	}
	
	public String listarHoy(){
		TaskService tService;
		actual = "hoy";
		try{
			tService = Services .getTaskService();
			
			tasks = tService.findTodayTasksByUserId(user.getId());
			
			return "listadoTareas";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		
	}
	
	public String listarSemana(){
		actual = "semana";
		TaskService tService;
		try{
			tService = Services .getTaskService();
			
			tasks = tService.findWeekTasksByUserId(user.getId());
			
			return "listadoTareas";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	public String listarCategoria(Category categoria){
		TaskService tService;
		try{
			tService = Services.getTaskService();
			
			//Quizas mejor por solo ID
			tasks = tService.findTasksByCategoryId(categoria.getId());
			
			return "listadoTareas";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	public void listarCategorias(){
		TaskService tService;
		List<Category> cat;
		try{
			tService=Services.getTaskService();
			cat = tService.findCategoriesByUserId(user.getId());
			for(Category c : cat)
				categories.put(c.getName(), String.valueOf(c.getId()));			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean delayed(Task t){
		return t.getPlanned().before(DateUtil.today());
	}
	
	public boolean finished(Task t){
		return t.getFinished() != null;
	}
	
	public String userCat(Task t){	//TODO change
		Long id = t.getCategoryId();
		TaskService tService;
		if(t.getCategoryId() != null){
			try{
				tService = Services.getTaskService();
				Category cat = tService.findCategoryById(id);
				return cat.getName();
			} catch(Exception e){
				e.printStackTrace();
				return " ";
			}
		}
		return " ";
	}
	
	public void createTask(){
		String redirect = actual;
		Task t = new Task();
		t.setTitle(createTaskBean.getTitle());
		t.setPlanned(createTaskBean.getPlanned());
		
		if(t.getPlanned().after(DateUtil.yesterday()) && t.getPlanned().before(DateUtil.tomorrow()))
			redirect = "hoy";
		else if(t.getPlanned().after(DateUtil.today()))
			redirect = "semana";
		
		t.setComments(createTaskBean.getComments());
		
		if(createTaskBean.getCategoryId() != 0)
			t.setCategoryId(createTaskBean.getCategoryId());
		else
			redirect = "inbox";
		
		t.setCreated(new Date());
		User u = (User) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("logedUser");
		t.setUserId(u.getId());
		
		TaskService tService = Services.getTaskService();
		try {
			tService.createTask(t);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		reloadList(redirect);
		createTaskBean.init();
		RequestContext.getCurrentInstance().execute("PF('create-task-dialog').hide();");
	}
	
	public void editTask(){
		Task t = new Task();
		
		t.setTitle(editTaskBean.getTitle());
		t.setPlanned(editTaskBean.getPlanned());
		t.setComments(editTaskBean.getComments());
		t.setCategoryId(editTaskBean.getCategoryId());
		
		TaskService tService = Services.getTaskService();
		try {
			tService.updateTask(t);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		reloadList(actual);
		RequestContext.getCurrentInstance().execute("PF('edit-task-dialog').hide();");
	}
	
	private void reloadList(String list){
		if(list == "inbox")
			listarInbox();
		else if(list== "hoy")
			listarHoy();
		else if(list == "semana")
			listarSemana();
	}
	
//	public String editTask(Task tarea){
//		TaskService tService;
//		try{
//			
//		}
//		catch{
//			
//		}
//	}
	
	public void changeFilter(ActionEvent e){
		showFinished=!showFinished;
//		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
//		DataTable table = (DataTable) viewRoot.findComponent("form-tasks:table-tasks");
//		table.resetValue();
	}
	
	

}
