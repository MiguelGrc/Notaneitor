package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sdi.business.Services;
import com.sdi.business.UserService;
import com.sdi.dto.User;

@ManagedBean(name = "login")
@SessionScoped
public class RegisterBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	private String email;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String register(){
		UserService uService;
		try{
			uService= Services.getUserService();
			User u= new User();
			
			u.setLogin(login).setPassword(password).setEmail(email);
			uService.registerUser(u);
			return "login";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		
		
		
	}
	
	
}
