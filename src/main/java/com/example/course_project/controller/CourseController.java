package com.example.course_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.course_project.service.ifs.CourseService;
import com.example.course_project.vo.CourseRequest;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;

@RestController
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	//新增課程
	@PostMapping(value = "/api/createcourse")
	public CourseResponse createCourse(@RequestBody CourseRequest request) {
		return courseService.createCourse(request);
	}
	//刪除課程
	@PostMapping(value = "/api/deletecourse")
	 public CourseResponse deleteById(@RequestBody CourseRequest req) {
	  return courseService.deleteCourseById(req);
	 }
	//用id,name尋找課程
	@GetMapping(value = "/api/findcourse")
	public CourseResponse getCourse(@RequestParam(required = false) String courseCode,
			@RequestParam(required = false) String courseName) {
		return courseService.getCourse(courseCode, courseName);
	}
	//尋找已選課程
	@GetMapping(value = "/api/findstudentcourse")
	public StudentResponse findStudentCourse(@RequestParam String studentId) {
		return courseService.findStudentCourse(studentId);
	}
	//加選
	@PostMapping(value = "/api/addcourse")
	public AddCourseResponse addCourse(@RequestBody AddCourseRequest request) {
		return courseService.addCourse(request);
	}
	//修改
	@PostMapping(value = "/api/revisecourse")
	 public CourseResponse reviseCourse(@RequestBody CourseRequest req) {
	  return courseService.reviseCourse(req);
	 }
	//刪除已選課程
	@GetMapping(value = "/api/deletestudentcourse")
	public StudentResponse deleteCourse(@RequestParam String studentId,@RequestParam String courseCode) {
		return courseService.deleteCourse(studentId,courseCode);
	}
}
