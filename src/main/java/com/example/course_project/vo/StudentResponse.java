package com.example.course_project.vo;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;

import com.example.course_project.entity.StudentCourse;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse {

	private String studentId;

	private String studentName;

	private String coursecode;

	private String coursename;
	
	private String courseday;
	
	private LocalTime coursestart;
	
	private LocalTime courseend;
	
	private int creddit;

	private List<StudentCourse> studentlist;
	
	private String message;

	public StudentResponse() {

	}

	public StudentResponse(List<StudentCourse> studentlist) {
		this.studentlist = studentlist;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCourseday() {
		return courseday;
	}

	public void setCourseday(String courseday) {
		this.courseday = courseday;
	}

	public LocalTime getCoursestart() {
		return coursestart;
	}

	public void setCourseStart(LocalTime coursestart) {
		this.coursestart = coursestart;
	}

	public LocalTime getCourseend() {
		return courseend;
	}

	public void setCourseEnd(LocalTime courseend) {
		this.courseend = courseend;
	}

	public int getCreddit() {
		return creddit;
	}

	public void setCreddit(int creddit) {
		this.creddit = creddit;
	}

	public List<StudentCourse> getStudentlist() {
		return studentlist;
	}

	public void setStudentlist(List<StudentCourse> studentlist) {
		this.studentlist = studentlist;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
