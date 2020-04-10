package com.forum.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.forum.forum.controller.dto.TopicDTO;
import com.forum.forum.controller.dto.TopicDetailDTO;
import com.forum.forum.controller.form.TopicForm;
import com.forum.forum.controller.form.UpdateTopicForm;
import com.forum.forum.model.Topic;
import com.forum.forum.repository.CourseRepository;
import com.forum.forum.repository.TopicRepository;

@RestController
@RequestMapping("/topic")
public class TopicController {
	
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private CourseRepository courseRepository;

	@GetMapping
	public List<TopicDTO> list(String courseName){
		System.out.println(courseName);
		if (courseName == null) {
			return TopicDTO.toTopic(topicRepository.findAll());
		} else {
			return TopicDTO.toTopic(topicRepository.findByCourseName(courseName));
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicDTO> save(@RequestBody @Valid TopicForm form, UriComponentsBuilder uriBuilder) {
		Topic topic = form.toTopic(courseRepository);
		topicRepository.save(topic);
		
		URI uri = uriBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicDTO(topic));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TopicDetailDTO> detail(@PathVariable Long id) {
		Optional<Topic> topic = topicRepository.findById(id);
		if(topic.isPresent()) {
			TopicDetailDTO details = new TopicDetailDTO(topic.get()); 
			return ResponseEntity.ok(details);
		}
		return ResponseEntity.notFound().build();		
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicForm form){
		Optional<Topic> optional = topicRepository.findById(id);
		if(optional.isPresent()) {
			Topic topic = form.toTopic(id, topicRepository);
			return ResponseEntity.ok(new TopicDTO(topic));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable long id){
		Optional<Topic> optional = topicRepository.findById(id);
		if(optional.isPresent()) {
			topicRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
