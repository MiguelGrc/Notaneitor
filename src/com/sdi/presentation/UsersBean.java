package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import alb.util.log.Log;

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
	private ReiniciarBDBean reiniciarBD;

	public ReiniciarBDBean getReiniciarBD() {
		return reiniciarBD;
	}

	public void setReiniciarBD(ReiniciarBDBean reiniciarBD) {
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
	
	@PostConstruct
	public void init(){
		listarNoAdmins();
	}
	
	public String listarNoAdmins(){
		AdminService aService;
		try{
			aService= Services.getAdminService();
			users = filtrarNoAdmin(aService.findAllUsers());
			
			Log.debug("Usuarios listados");
			return "success";
		}catch(Exception e){
			Log.error(e);
			return "error";
		}
		
	}
	
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
				Log.debug("Usuario %s eliminado", selected.getLogin());
				// Actualizamos el javabean de users inyectado en la tabla.
				users = filtrarNoAdmin(service.findAllUsers());
			}
		} catch (Exception ex) {
			Log.error(ex);
		}
	}
	
	public void cambiarEstado(ActionEvent e) {
		AdminService service;
		try {
			service = Services.getAdminService();
			if(selected != null){
				if(selected.getStatus() == UserStatus.ENABLED){
					service.disableUser(selected.getId());
					Log.debug("Usuario %s deshabilitado", selected.getLogin());
				}
				else
					service.enableUser(selected.getId());
				Log.debug("Usuario %s habilitado", selected.getLogin());
				// Actualizamos el javabean de users inyectado en la tabla.
				users = filtrarNoAdmin(service.findAllUsers());
			}
		} catch (Exception ex) {
			Log.error(ex);
		}
	}
	
	public void reiniciarBDAction(ActionEvent e){
		try {
			reiniciarBD.reiniciar();
			Log.debug("Base de datos reiniciada");
			users = filtrarNoAdmin(Services.getAdminService().findAllUsers());
		} catch (BusinessException ex) {
			Log.error("Error al reiniciar la base de datos");
		}
	}
	
	public void onRowSelect(SelectEvent event) {
	    disabled = false;
	}
	

}
