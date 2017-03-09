package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;

import com.sdi.business.AdminService;
import com.sdi.business.Services;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;

@ManagedBean(name="users")
@SessionScoped
public class UsersBean {
	
	private List<User> users;
	private User selected;
	private boolean disabled = true;

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public User getSelected() {
		return selected;
	}

	public void setSelected(User selected) {
		this.selected = selected;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@PostConstruct
	public void init(){
		listarNoAdmins();
	}
	
	public String listarNoAdmins(){
		AdminService aService;
		try{
			aService= Services.getAdminService();
			// Si no funciona casting a Alumno[]
			users = filtrarNoAdmin(aService.findAllUsers());
			
			return "listado"; //Quizas mejor con a nomenclatura exit y cambiar mapa de navegacion.
		}catch(Exception e){
			return "error";
		}
		
	}
	
	//TODO: SI SE REGISTRA UN NUEVO USUARIO, NO APARECE EN LA LISTA DEL ADMIN HASTA QUE SE REINICIA LA APLICACION!!!
	
	private List<User> filtrarNoAdmin(List<User> usuarios){
		List<User> noAdminUsers = new ArrayList<User>();
		for(User user: usuarios){
			if(!user.getIsAdmin())
				noAdminUsers.add(user);
		}
		
		return noAdminUsers;
	}
	
	public String baja() {
		AdminService service;
		try {
			service = Services.getAdminService();
			if(selected != null){
				service.deepDeleteUser(selected.getId());
				// Actualizamos el javabean de users inyectado en la tabla.
				users = filtrarNoAdmin(service.findAllUsers());
			}
			return "exito";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public String cambiarEstado() {
		AdminService service;
		try {
			service = Services.getAdminService();
			if(selected != null){
				if(selected.getStatus() == UserStatus.ENABLED)
					service.disableUser(selected.getId());
				else
					service.enableUser(selected.getId());
				// Actualizamos el javabean de users inyectado en la tabla.
				users = filtrarNoAdmin(service.findAllUsers());
			}
			return "exito";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public void onRowSelect(SelectEvent event) {
	    disabled = false;
	}
	

}
