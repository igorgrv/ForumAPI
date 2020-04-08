package com.forum.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forum.forum.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
}
