package com.forum.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.forum.forum.model.Topic;
import com.forum.forum.model.TopicStatus;

public class TopicDetailDTO {

	private Long id;
	private String title, post;
	private LocalDateTime creationDate;
	private String userName;
	private TopicStatus status;
	private List<AnswerDTO> answers;
	
	public TopicDetailDTO(Topic topic) {
		this.id = topic.getId();
		this.title = topic.getTitle();
		this.post = topic.getPost();
		this.creationDate = topic.getCreationDate();
		this.userName = topic.getUser().getName();
		this.status = topic.getStatus();
		this.answers = new ArrayList<>();
		this.answers.addAll(topic.getRespostas().stream().map(AnswerDTO::new).collect(Collectors.toList()));
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
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

	public TopicStatus getStatus() {
		return status;
	}

	public List<AnswerDTO> getAnswers() {
		return answers;
	}

}
