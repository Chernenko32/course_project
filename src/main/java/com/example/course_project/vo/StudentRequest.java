package com.example.course_project.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentRequest {
	@JsonProperty("student_id")
	private String studentId;

	public StudentRequest() {

	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

}
