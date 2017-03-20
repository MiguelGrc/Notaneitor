package com.sdi.presentation.validators;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sdi.business.AdminService;
import com.sdi.business.Services;
import com.sdi.dto.User;

public class UsernameValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent uiComponent, Object value)
			throws ValidatorException {
		
		AdminService aServ;
		try{
			aServ = Services.getAdminService();
			List<User> existentUsers = aServ.findAllUsers();
			for(User u: existentUsers){
				if(u.getLogin().equals(value.toString())){
					throw new ValidatorException(new FacesMessage("An user with this login already exists."));
				}
			}
			
		}
		catch(Exception e){
			throw new ValidatorException(new FacesMessage("An error has ocurred"));
		}
		
	}

}
