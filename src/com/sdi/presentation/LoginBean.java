package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.Services;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;

@ManagedBean(name = "login")
@RequestScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = -3194940809153687908L;
	
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


	public String login(){
		UserService service;		
		try {
			service = Services.getUserService();
			User u = service.findLoggableUser(login, password);
			if(u != null){
				//Filtrar si es admin o no. Admin->listarUsuarios / NoAdmin->listadoTareas
				 FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("logedUser", u);
				 if(u.getIsAdmin())
					 return "admin";
				 else
					 return "user";
			}
			else{
				//Devolver a la misma página
				return "failure";
			}
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("logedUser");
		return "error";
	}
}
