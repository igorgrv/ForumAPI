package com.forum.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.forum.controller.dto.TopicDTO;
import com.forum.forum.model.Course;
import com.forum.forum.model.Topic;

@RestController
public class TopicController {

	@RequestMapping("/firstEndPoint")
	public List<TopicDTO> list(){
		Topic topic = new Topic("Doubt", "API Doubt", new Course("Java", "API"));
		
		return TopicDTO.toTopic(Arrays.asList(topic, topic, topic));
	}
	
}
