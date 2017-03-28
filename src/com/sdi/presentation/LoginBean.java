 package com.sdi.presentation;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;
import alb.util.log.LogLevel;

import com.sdi.business.Services;
import com.sdi.business.UserService;
import com.sdi.dto.User;

@ManagedBean(name = "login")
@RequestScoped
public class LoginBean {
	
	private String login;
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@PostConstruct
	public void init(){
		//Inicializamos el log para este usuario.
		Log.setLogLevel(LogLevel.DEBUG);
	}

	public String login(){
		UserService service;		
		try {
			service = Services.getUserService();
			User u = service.findLoggableUser(login, password);
			password = "";
			
			if(u != null){
				 FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("logedUser", u);
				 if(u.getIsAdmin()){
					 Log.debug("Administrador %s logeado", u.getLogin());
					 return "admin";
				 }
				 else{
					 Log.debug("Usuario %s logeado", u.getLogin());
					 return "user";
				 }
			}
			else{
				Log.debug("Usuario %s no encontrado o deshabilitado", login);
				return "failure";
			}
			
		} catch (Exception e) {
			Log.error(e);
			return "error";
		}
	}
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("logedUser");
		Log.debug("Usuario deslogeado");
		return "/login.xhtml";
	}
}
