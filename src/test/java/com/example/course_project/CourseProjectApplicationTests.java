package com.example.course_project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.course_project.constants.Week;

//@SpringBootTest
class CourseProjectApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(Week.checkWeeek("MONDAY"));
		System.out.println(Week.checkWeeek("MON"));
	}

}
