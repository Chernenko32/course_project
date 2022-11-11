package com.example.course_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.course_project.entity.StudentCourse;

@Repository
public interface StudentDao extends JpaRepository<StudentCourse, String> {

	public List<StudentCourse> findAllByStudentId(String studentId);
	@Modifying 
	@Transactional
	public List<StudentCourse> deleteBycourseCode(String courseCode);
	
	public List<StudentCourse> getBycourseCode(String courseCode);
	
	public List<StudentCourse> findByStudentId(String studentId);
	
	//StudentCourse findByStudentId(String studentId);
}
