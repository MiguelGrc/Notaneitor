package com.sdi.presentation;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import alb.util.log.Log;
import alb.util.log.LogLevel;

@ManagedBean(name = "settings")
@SessionScoped
public class SettingsBean implements Serializable {
	private static final long serialVersionUID = 2L;
	private static final Locale ENGLISH = new Locale("en");
	private static final Locale SPANISH = new Locale("es");
	private Locale locale = new Locale("es");

	@PostConstruct
	public void init(){
		Log.setLogLevel(LogLevel.DEBUG);
	}
	
	public Locale getLocale() {
		return (locale);
	}

	public void setSpanish(ActionEvent event) {
		locale = SPANISH;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		Log.debug("Idioma cambiado a Español");
	}

	public void setEnglish(ActionEvent event) {
		locale = ENGLISH;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		Log.debug("Idioma cambiado a Inglés");
	}
}
