package com.forum.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String post;
	private LocalDateTime creationDate = LocalDateTime.now();
	
	//For the "enum" Strings be saved (and not their order), it is necessary to use the annotation below.
	@Enumerated(EnumType.STRING)
	private TopicStatus status = TopicStatus.NOT_ANSWERED;
	
	@ManyToOne
	private User user;
	@ManyToOne
	private Course course;
	
	@OneToMany(mappedBy = "topic")
	private List<Answer> answers = new ArrayList<>();

	/**
	 * @deprecated hibernat only
	 */
	public Topic() {}
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
