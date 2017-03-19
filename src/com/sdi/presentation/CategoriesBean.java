package com.sdi.presentation;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.dto.Category;
import com.sdi.dto.User;
import com.sun.xml.internal.ws.wsdl.writer.document.Service;

public class CategoriesBean {
	
	private List<Category> categories;
	
	private String name;
	
	private User user;
	
	
	@PostConstruct
	public void init(){
		user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("logedUser"));
	}
	

	//Esto quizas deberia ser un evento y deberia ser llamado cuando se entra en el listado de tareas
	//Y cuando se añade una categoria o se elimina una
	public void listarCategorias(ActionEvent e){
		TaskService catServ;
		try{
			catServ=Services.getTaskService();
			
			categories= catServ.findCategoriesByUserId(user.getId());
			
			//Quizas es mejor hacer un controller y hacer que devuelve null y error si va mal.
		}
		catch(Exception error){
			//No se que poner ahora aqui.
		}
	}
	
	public void añadirCategoria(ActionEvent e){
		TaskService catServ;
		try{
			catServ=Services.getTaskService();
			
			Category category = new Category();
			category.setName(this.name);
			category.setUserId(user.getId());
			catServ.createCategory(category);
			
			categories= catServ.findCategoriesByUserId(user.getId());
			
		}
		catch(Exception error){
			//No se que poner ahora aqui.
		}
	}
}
