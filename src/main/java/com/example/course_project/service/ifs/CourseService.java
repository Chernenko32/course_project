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
	//刪除課程
	public Course deleteCourseById(String courseCode);
	//用id,name尋找課程
	public List<CourseResponse> getCourse(String courseCode, String courseName);
	//尋找已選課程
	public StudentResponse findStudentCourse(String studentId);
	//加選
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest);
	//修改
	public Course reviseCourse(String courseCode, String courseName, String courseDay, LocalTime courseStart,
			   LocalTime courseEnd, int credit);
	
	public boolean hasCourseCode(String courseCode);
}
