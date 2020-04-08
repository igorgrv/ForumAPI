package com.forum.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.forum.controller.dto.TopicDTO;
import com.forum.forum.repository.TopicRepository;

@RestController
public class TopicController {
	
	@Autowired
	private TopicRepository topicRepository;

	@RequestMapping("/firstEndPoint")
	public List<TopicDTO> list(){
//		Topic topic = new Topic("Doubt", "API Doubt", new Course("Java", "API"));
//		return TopicDTO.toTopic(Arrays.asList(topic, topic, topic));
		return TopicDTO.toTopic(topicRepository.findAll());
	}
	
}
