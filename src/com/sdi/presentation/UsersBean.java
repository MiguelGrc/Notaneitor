package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sdi.business.AdminService;
import com.sdi.business.Services;
import com.sdi.dto.User;

@ManagedBean(name="Users")
@SessionScoped
public class UsersBean {
	
	private User[] users;

	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}
	
	
	public String listarNoAdmins(){
		AdminService aService;
		try{
			aService= Services.getAdminService();
			// Si no funciona casting a Alumno[]
			users= filtrarNoAdmin(aService.findAllUsers()).toArray(new User[0]);
			
			return "listado"; //Quizas ,ejor con a nomenclatura exit y cambiar mapa de navegacion.
		}catch(Exception e){
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
	

}
