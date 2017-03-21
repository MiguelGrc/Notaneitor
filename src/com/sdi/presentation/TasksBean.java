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
	
	private List<Task> tareas;
	
	//TODO: mirar si es mejor crear otra clase?
	private List<Category> categorias;
	
	private boolean showFinished;
	
	private User user;
	
	public List<Task> getTareas() {
		return tareas;
	}

	public void setTareas(List<Task> tareas) {
		this.tareas = tareas;
	}
	
	
	
	public List<Category> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Category> categorias) {
		this.categorias = categorias;
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
			
			tareas = tService.findFinishedInboxTasksByUserId(user.getId());
			
			if(showFinished){
				tareas.addAll(tService.findFinishedInboxTasksByUserId(user.getId()));
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
			
			tareas = tService.findTodayTasksByUserId(user.getId());
			
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
			
			tareas = tService.findWeekTasksByUserId(user.getId());
			
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
			tareas = tService.findTasksByCategoryId(categoria.getId());
			
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
			
			categorias=tService.findCategoriesByUserId(user.getId());
			
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
