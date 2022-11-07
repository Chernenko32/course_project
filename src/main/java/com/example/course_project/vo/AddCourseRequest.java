package com.example.course_project.vo;

public class AddCourseRequest {

	private String id;
	
	private String studentId;
	
	private String courseCode;
	
	private String studentName;
	
	public AddCourseRequest() {
		
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

	public String getCoursecode() {
		return courseCode;
	}

	public void setCoursecode(String coursecode) {
		this.courseCode = coursecode;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
}
