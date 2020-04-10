package com.forum.forum.controller.dto;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

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
	
	//Using the map method, for each topic, a "topicDTO" will be created
	public static Page<TopicDTO> toTopic(Page<Topic> topics) {
		return topics.map(TopicDTO::new);
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
