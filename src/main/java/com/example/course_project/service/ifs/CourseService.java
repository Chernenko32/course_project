package com.example.course_project.service.ifs;

import java.time.LocalTime;
import java.util.List;

import com.example.course_project.entity.Course;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;

public interface CourseService {
	//新增課程
	public CourseResponse createCourse(String courseCode, String courseName, String courseDay, LocalTime courseStart,
			LocalTime courseEnd, int credit);
	
	public Course findById(String courseCode);

	public List<CourseResponse> getCourse(String courseCode, String courseName);
	
	public List<StudentResponse> findStudentCourse(String studentId);

	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest);
	
	public Course reviseCourse(String courseCode, String courseName, String courseDay, LocalTime courseStart,
			   LocalTime courseEnd, int credit);
}
