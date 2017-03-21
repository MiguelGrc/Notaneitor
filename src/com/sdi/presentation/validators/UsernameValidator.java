package com.sdi.presentation.validators;

import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sdi.business.AdminService;
import com.sdi.business.Services;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.User;

@FacesValidator("usernameValidator")
public class UsernameValidator implements Validator {

	//TODO: mirar como hacemos al final el tema de las validaciones de usuario repetido y de email. 
	//		El email da problemas por los checks en persistencia, quizá podamos incluso quitar los de Alberto
	
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
		catch(BusinessException e){
			throw new ValidatorException(new FacesMessage("An error has ocurred"));		//TODO: redirección a página de error??
		}
		
	}

}
