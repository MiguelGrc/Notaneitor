package com.sdi.presentation;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;

@ManagedBean(name="tareas")
@SessionScoped
public class TareasBean {
	
	private List<Task> tareas;
	
	private User user;
	
	public List<Task> getTareas() {
		return tareas;
	}

	public void setTareas(List<Task> tareas) {
		this.tareas = tareas;
	}
	
	@PostConstruct
	public void init(){
		user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("logedUser"));
	}
	
	
	
	

	public String listarInbox(){
		TaskService tService;
		try{
			tService = Services.getTaskService();
			
			tareas = tService.findFinishedInboxTasksByUserId(user.getId());
			
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
	
	

}
