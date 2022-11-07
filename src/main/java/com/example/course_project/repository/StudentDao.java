package com.example.course_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_project.entity.StudentCourse;

@Repository
public interface StudentDao extends JpaRepository<StudentCourse, String> {

	List<StudentCourse> findAllByStudentId(String studentId);
	
	
	
	//StudentCourse findByStudentId(String studentId);
}
