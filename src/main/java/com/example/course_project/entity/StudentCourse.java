package com.example.course_project.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "studentcourse")
public class StudentCourse {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "student_id")
	private String studentId;
	@Column(name = "student_name")
	private String studentName;
	@Column(name = "course_code")
	private String courseCode;
	@Column(name = "course_name")
	private String courseName;
	@Column(name = "course_day")
	private String courseDay;
	@Column(name = "course_start")
	private LocalTime courseStart;
	@Column(name = "course_end")
	private LocalTime courseEnd;
	@Column(name = "credit")
	private int credit;
	
	public StudentCourse() {

	}

	public StudentCourse(String id, String studentId, String studentName, String courseCode, String courseName,
			String courseDay, LocalTime courseStart, LocalTime courseEnd, int credit) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.studentName = studentName;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.courseDay = courseDay;
		this.courseStart = courseStart;
		this.courseEnd = courseEnd;
		this.credit = credit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	
}
