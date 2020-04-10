package com.forum.forum.controller.dto;

import java.time.LocalDateTime;

import com.forum.forum.model.Answer;

public class AnswerDTO {

	private Long id;
	private String post;
	private LocalDateTime creationDate;
	private String userName;
	
	public AnswerDTO(Answer answer) {
		this.id = answer.getId();
		this.post = answer.getPost();
		this.creationDate = answer.getCreationDate();
		this.userName = answer.getUser().getName();
	}

	public Long getId() {
		return id;
	}

	public String getPost() {
		return post;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public String getUserName() {
		return userName;
	}
	
}
