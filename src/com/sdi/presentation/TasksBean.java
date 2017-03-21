package com.sdi.presentation;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;

@ManagedBean(name="tasks")
@SessionScoped
public class TasksBean {
	
	private List<Task> tasks;
	
	//TODO: mirar si es mejor crear otra clase?
	private List<Category> categories;
	private boolean showFinished;
	private User user;
	private Task selected;
	
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
	
	
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@PostConstruct
	public void init(){
		user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("logedUser"));
		
		//TODO: esto estaría bien?
		listarInbox();
		listarCategorias();
		
	}
	
	
	
	

	public String listarInbox(){
		TaskService tService;
		try{
			tService = Services.getTaskService();
			
			tasks = tService.findFinishedInboxTasksByUserId(user.getId());
			
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
	
	public String listarSemanal(){
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
			tService = Services .getTaskService();
			
			//Quizas mejor por solo ID
			tasks = tService.findTasksByCategoryId(categoria.getId());
			
			return "listadoTareas";
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	public String listarCategorias(){
		TaskService tService;
		try{
			tService=Services.getTaskService();
			
			categories=tService.findCategoriesByUserId(user.getId());
			
			return "listadoTareas";
			
		}
		catch(Exception e){
			e.printStackTrace();
			return "error";
		}
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
	}
	
	

}
