package com.example.course_project.service.ifs;

import java.time.LocalTime;
import java.util.List;

import com.example.course_project.entity.Course;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseRequest;

public interface CourseService {
	//新增課程
	public CourseResponse createCourse(CourseRequest req);
	//刪除課程
	public CourseResponse deleteCourseById(CourseRequest req);
	//用id,name尋找課程
	public CourseResponse getCourse(String courseCode, String courseName);
	//尋找已選課程
	public StudentResponse findStudentCourse(String studentId);
	//加選
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest);
	//修改
	public CourseResponse reviseCourse(CourseRequest req);
	//自定義方法
	public boolean hasCourseCode(String courseCode);
	//退選
	public StudentResponse deleteCourse(String studentId,String courseCode);
}
