package com.sdi.presentation.validators;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import alb.util.log.Log;

import com.sdi.business.AdminService;
import com.sdi.business.Services;
import com.sdi.dto.User;

@FacesValidator("usernameValidator")
public class UsernameValidator implements Validator {
 
	//El email daría problemas de estar duplicado por los checks en la capa de negocio dada
	
	@Override
	public void validate(FacesContext context, UIComponent uiComponent, Object value)
			throws ValidatorException {
		
		AdminService aServ;
		try{
			aServ = Services.getAdminService();
			List<User> existentUsers = aServ.findAllUsers();
			for(User u: existentUsers){
				if(u.getLogin().equals(value.toString())){
					ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
					String message = bundle.getString("form_username_validatorMessage");
					FacesMessage facesMessage = new FacesMessage(message);
					throw new ValidatorException(facesMessage);
				}
			}
			
		}
		catch(ValidatorException ve){
			throw ve;
		}
		catch(Exception e){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
			} catch (IOException e1) {
				Log.error(e1);
			}
			Log.error(e);
		}
		
	}

}
