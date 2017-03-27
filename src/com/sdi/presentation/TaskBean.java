package com.sdi.presentation;

import java.util.Date;

import alb.util.date.DateUtil;

import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;

public class TaskBean {
	//Esta clase actua como un "adapter" de la clase original Task
	
	private Task task;
	
	private Long id;

	private String title;
	private String comments;
	private String categ;
	private String foundCateg;
	private Date created = DateUtil.today();
	private Date planned;
	private Date finished;
	
	private Long categoryId;
	private Long userId;
	
	public TaskBean(){
		task=new Task();
	}
	
	public TaskBean(Task task){
		this.task=task;
	}
	
	public Long getId() {
		return task.getId();
	}
	public void setId(Long id) {
		task.setId(id);
	}
	public String getTitle() {
		return task.getTitle();
	}
	public void setTitle(String title) {
		task.setTitle(title);
	}
	public String getComments() throws BusinessException {
		TaskService tServ;
		if(foundCateg==null){
			tServ= Services.getTaskService();
			foundCateg= tServ.findCategoryById(this.id).getName();
		}
		return foundCateg;
	}
	public void setComments(String comments) {
		this.foundCateg = comments;
	}
	
//	public String getCateg() {
//		return task.getCategoryId();
//	}
//	public void setCateg(String categ) {
//		this.categ = categ;
//	}
	public Date getCreated() {
		return task.getCreated();
	}
	public void setCreated(Date created) {
		task.setCreated(created);
	}
	public Date getPlanned() {
		return task.getPlanned();
	}
	public void setPlanned(Date planned) {
		task.setPlanned(planned);
	}
	public Date getFinished() {
		return task.getFinished();
	}
	public void setFinished(Date finished) {
		task.setFinished(finished);
	}
	public Long getCategoryId() {
		return task.getCategoryId();
	}
	public void setCategoryId(Long categoryId) {
		task.setCategoryId(categoryId);
	}
	public Long getUserId() {
		return task.getUserId();
	}
	public void setUserId(Long userId) {
		task.setUserId(userId);
	}
	
	
	
	

}
