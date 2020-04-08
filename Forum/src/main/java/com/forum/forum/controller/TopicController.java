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

	@RequestMapping("/topic")
	public List<TopicDTO> list(String courseName){
		System.out.println(courseName);
		if (courseName == null) {
			return TopicDTO.toTopic(topicRepository.findAll());
		} else {
			return TopicDTO.toTopic(topicRepository.findByCourseName(courseName));
		}
	}
	
}
