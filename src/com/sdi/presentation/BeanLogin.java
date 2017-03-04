package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sdi.business.Services;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;

@ManagedBean(name = "login")
@SessionScoped
public class BeanLogin implements Serializable {
	private static final long serialVersionUID = -3194940809153687908L;
	
	private User user = new User();
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String login(){
		UserService service;		
		try {
			service = Services.getUserService();
			User u = service.findLoggableUser(user.getLogin(), user.getPassword());
			if(u != null){
				//Filtrar si es admin o no. Admin->listarUsuarios / NoAdmin->listadoTareas
				return "login";
			}
			else{
				//Devolver a la misma página
				return "error";
			}
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
}
