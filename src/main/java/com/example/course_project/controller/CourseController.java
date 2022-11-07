package com.example.course_project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.course_project.entity.Course;
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

	@PostMapping(value = "/api/createcourse")
	public CourseResponse createcourse(@RequestBody CourseRequest request) {
		return courseService.createCourse(request.getCourseCode(), request.getCourseName(), request.getCourseDay(),
				request.getCourseStart(), request.getCourseEnd(), request.getCredit());
	}

	@PostMapping(value = "/api/deletecourse")
	public CourseResponse findById(@RequestBody CourseRequest req) {
		CourseResponse res = new CourseResponse();
		Course course = courseService.findById(req.getCourseCode());
		// 防呆
		if (course == null) {
			res.setMessage("請輸入課程代碼!!");
			return res;
		}
		res.setMessage("課程刪除成功!!");
		return res;
	}

	@GetMapping(value = "/api/findcourse")
	public List<CourseResponse> getCourse(@RequestParam(required = false) String coursecode,
			@RequestParam(required = false) String coursename) {
		return courseService.getCourse(coursecode, coursename);
	}

	@GetMapping(value = "/api/findstudentcourse")
	public List<StudentResponse> findStudentCourse(@RequestParam String studentId) {
		return courseService.findStudentCourse(studentId);
	}

	@PostMapping(value = "/api/addcourse")
	public AddCourseResponse addCourse(@RequestBody AddCourseRequest request) {
		return courseService.addCourse(request);
	}

	@PostMapping(value = "/api/revisecourse")
	public CourseResponse reviseCourse(@RequestBody CourseRequest req) {
		CourseResponse res = new CourseResponse();
		Course course = courseService.reviseCourse(req.getCourseCode(), req.getCourseName(), req.getCourseDay(),
				req.getCourseStart(), req.getCourseEnd(), req.getCredit());
		// 防呆
		if (course == null) {
			res.setMessage("請輸入課程代碼!!");
			return res;
		}
		res.setCourseCode(course.getCourseCode());
		res.setCourseName(course.getCourseName());
		res.setCourseDay(course.getCourseDay());
		res.setCourseStart(course.getCourseStart());
		res.setCourseEnd(course.getCourseEnd());
		res.setCredit(course.getCredit());
		res.setMessage("課程修改成功!!");
		return res;
	}
}
