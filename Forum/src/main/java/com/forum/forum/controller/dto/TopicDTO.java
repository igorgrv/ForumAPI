package com.forum.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.forum.forum.model.Topic;

public class TopicDTO {

	private Long id;
	private String title, post;
	private LocalDateTime creationDate;
	
	public TopicDTO(Topic topic) {
		this.id = topic.getId();
		this.title = topic.getTitle();
		this.post = topic.getPost();
		this.creationDate = topic.getCreationDate();
	}
	
	public static List<TopicDTO> toTopic(List<Topic> topics) {
		return topics.stream().map(TopicDTO::new).collect(Collectors.toList());
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


}
