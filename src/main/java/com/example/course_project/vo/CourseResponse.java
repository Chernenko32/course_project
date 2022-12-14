package com.example.course_project.vo;

import java.time.LocalTime;
import java.util.List;

import com.example.course_project.entity.Course;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponse {

	private String courseCode;

	private String courseName;

	private String message;

	private String courseDay;

	private LocalTime courseStart;

	private LocalTime courseEnd;

	private Integer credit;

	private List<Course> courseList;

	public CourseResponse(String courseCode, String courseName, String courseDay, LocalTime courseStart,
			LocalTime courseEnd, Integer credit,String message) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseDay = courseDay;
		this.courseStart = courseStart;
		this.courseEnd = courseEnd;
		this.credit = credit;
		this.message=message;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public String getCourseDay() {
		return courseDay;
	}

	public void setCourseDay(String courseDay) {
		this.courseDay = courseDay;
	}

	public LocalTime getCourseStart() {
		return courseStart;
	}

	public void setCourseStart(LocalTime courseStart) {
		this.courseStart = courseStart;
	}

	public LocalTime getCourseEnd() {
		return courseEnd;
	}

	public void setCourseEnd(LocalTime courseEnd) {
		this.courseEnd = courseEnd;
	}

	public CourseResponse() {

	}

	public CourseResponse(List<Course> courseList) {
		this.courseList = courseList;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

}
