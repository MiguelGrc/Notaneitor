package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import com.sdi.business.AdminService;
import com.sdi.business.Services;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;

@ManagedBean(name="users")
@ViewScoped
public class UsersBean {
	
	private List<User> users;
	private User selected;
	private boolean disabled = true;
	
	@ManagedProperty(value="#{reiniciar}")
	private ReiniciarBD reiniciarBD;

	public ReiniciarBD getReiniciarBD() {
		return reiniciarBD;
	}

	public void setReiniciarBD(ReiniciarBD reiniciarBD) {
		this.reiniciarBD = reiniciarBD;
	}

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
	
	public UsersBean(){
		//Quizas es mejor otra opcion, como añadir siempre un listener a todas las peticiones y hacerlo session scoped otra vez.
		//users = filtrarNoAdmin(aService.findAllUsers());
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
	
	public void baja(ActionEvent e) {
		AdminService service;
		try {
			service = Services.getAdminService();
			if(selected != null){
				service.deepDeleteUser(selected.getId());
				// Actualizamos el javabean de users inyectado en la tabla.
				users = filtrarNoAdmin(service.findAllUsers());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void cambiarEstado(ActionEvent e) {
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void reiniciarBDAction(ActionEvent e){
		try {
			reiniciarBD.reiniciar();
			users = filtrarNoAdmin(Services.getAdminService().findAllUsers());
		} catch (BusinessException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void onRowSelect(SelectEvent event) {
	    disabled = false;
	}
	

}
