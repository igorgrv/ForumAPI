package com.forum.forum.controller.form;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.forum.forum.model.Topic;
import com.forum.forum.repository.TopicRepository;
import com.sun.istack.NotNull;

public class UpdateTopicForm {

	@NotNull @NotEmpty @Length(min = 5)
	private String title;
	
	@NotNull @NotEmpty @Length(min = 10)
	private String post;
	
	public Topic toTopic(Long id, TopicRepository topicRepository) {
		Topic topic = topicRepository.getOne(id);
		
		topic.setTitle(title);
		topic.setPost(post);
		return topic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

}
