package com.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Topic {

	private Long id;
	private String title;
	private String post;
	private LocalDateTime creationDate = LocalDateTime.now();
	private TopicStatus status = TopicStatus.NAO_RESPONDIDO;
	private User author;
	private Course course;
	private List<Answer> answers = new ArrayList<>();

	public Topic(String title, String post, Course course) {
		this.title = title;
		this.post = post;
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public TopicStatus getStatus() {
		return status;
	}

	public void setStatus(TopicStatus status) {
		this.status = status;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Course getCurso() {
		return course;
	}

	public void setCurso(Course course) {
		this.course = course;
	}

	public List<Answer> getRespostas() {
		return answers;
	}

	public void setRespostas(List<Answer> answers) {
		this.answers = answers;
	}

}
