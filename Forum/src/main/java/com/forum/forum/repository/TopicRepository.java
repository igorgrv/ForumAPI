package com.forum.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.forum.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long>{
}
