package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.sdi.business.Services;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;

@ManagedBean(name = "register")
@SessionScoped
public class RegisterBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String login;
	private String password;
	private String repeatedPassword;
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
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
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
			return "success";
		} catch(BusinessException e){
			e.printStackTrace();
			return "failure";
		}		
	}
	
	public void validatePassword(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		String password = context.getExternalContext().getRequestParameterMap().get("form-registro:password");
		String repeatedPassword = context.getExternalContext().getRequestParameterMap().get("form-registro:repeated-password");
		if(!password.equals(repeatedPassword)){
			ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
			String message = bundle.getString("form_repeatedPassword_validatorMessage");
			FacesMessage facesMessage = new FacesMessage(message);
			throw new ValidatorException(facesMessage);
		}
	}
	
	
}
