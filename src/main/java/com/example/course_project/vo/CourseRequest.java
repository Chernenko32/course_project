package com.example.course_project.vo;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseRequest {

	@JsonProperty("coursecode")
	private String courseCode;
	@JsonProperty("coursename")
	private String courseName;
	@JsonProperty("courseday")
	private String courseDay;
	@JsonProperty("coursestart")
	private LocalTime courseStart;
	@JsonProperty("courseend")
	private LocalTime courseEnd;
	@JsonProperty("credit")
	private Integer credit;

	public CourseRequest() {

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

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

}
