package com.sdi.presentation;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "createTask")
@ViewScoped
public class CreateTaskBean {
	
	private String title;
	private Date planned;
	private String comments;
	private long categoryId;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPlanned() {
		return planned;
	}
	public void setPlanned(Date planned) {
		this.planned = planned;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	@PostConstruct
	public void init(){
		title = null;
		this.planned = new Date();
		comments = null;
		categoryId = 0;
	}

}
