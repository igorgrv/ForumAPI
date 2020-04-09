package com.forum.forum.controller.form;

import com.forum.forum.model.Course;
import com.forum.forum.model.Topic;
import com.forum.forum.repository.CourseRepository;

public class TopicForm {

	private String title, post, courseName;
	
	public Topic toTopic(CourseRepository courseRepository) {
		Course course = courseRepository.findByName(this.courseName);
		Topic topic = new Topic(title, post, course);
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}
