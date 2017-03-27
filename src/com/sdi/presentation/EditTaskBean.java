package com.sdi.presentation;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.sdi.business.Services;
import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;

@ManagedBean(name = "editTask")
@ViewScoped
public class EditTaskBean {
	
	private String title;
	private Date planned;
	private String comments;
	private long categoryId;
	
	private Task task;
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
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
	
	//We load the task from the DB
	public void load(String id){
		if(id.isEmpty()){
			System.out.println("EMPRYYYYYY");
			return;
		}
		
		TaskService tService = Services.getTaskService();
		try {
			task = tService.findTaskById(Long.parseLong(id));
			
			title = task.getTitle();
			planned = task.getPlanned();
			comments = task.getComments();
			
			if(task.getCategoryId() != null)
				categoryId = task.getCategoryId();
			
			RequestContext.getCurrentInstance().execute("PF('edit-task-dialog').show();");
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
