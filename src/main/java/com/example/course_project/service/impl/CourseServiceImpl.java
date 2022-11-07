package com.example.course_project.service.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_project.entity.Course;
import com.example.course_project.entity.StudentCourse;
import com.example.course_project.repository.CourseDao;
import com.example.course_project.repository.StudentDao;
import com.example.course_project.service.ifs.CourseService;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentDao studentDao;

	@Override
	public CourseResponse createCourse(String courseCode, String courseName, String courseDay, LocalTime courseStart,
			LocalTime courseEnd, int credit) {
		CourseResponse res = new CourseResponse();
		if (courseCode == null || courseCode.isEmpty()) {
			res.setMessage("課程內容錯誤");
			return res;
		}
		Optional<Course> optional = courseDao.findById(courseCode);

		Course course = new Course();
		course.setCourseCode(courseCode);
		course.setCourseName(courseName);
		course.setCourseDay(courseDay);
		course.setCourseStart(courseStart);
		course.setCourseEnd(courseEnd);
		course.setCredit(credit);

		res.setCourseCode(course.getCourseCode());
		res.setCourseName(course.getCourseName());
		res.setCourseDay(course.getCourseDay());
		res.setCourseStart(course.getCourseStart());
		res.setCourseEnd(course.getCourseEnd());
		res.setCredit(course.getCredit());
		courseDao.save(course);
		res.setMessage("課程新增成功");
		return res;
	}


	@Override
	public Course findById(String coursecode) {
		Course course = new Course();
		Optional<Course> courseOp = courseDao.findById(coursecode);
		// 判斷資料庫內容
		if (courseOp.isPresent() || !StringUtils.hasText(coursecode)) {
			courseDao.deleteById(coursecode);
		}
		return course;
	}

	@Override
	public List<CourseResponse> getCourse(String coursecode, String coursename) {
		CourseResponse res = new CourseResponse();
		if (coursename == null) {
			Optional<Course> optionalcourse = courseDao.findById(coursecode);
			Course course = optionalcourse.orElse(null);
			if (course == null) {
				return new ArrayList<>();
			} else {
				res.setCourseCode(course.getCourseCode());
				res.setCourseName(course.getCourseName());
				res.setCredit(course.getCredit());
			}
			List<CourseResponse> resList = new ArrayList<>();
			resList.add(res);
			return resList;
		} else {
			List<Course> listCourse = courseDao.findByCourseName(coursename);
			List<CourseResponse> resList = new ArrayList<>();
			for (Course course : listCourse) {
				CourseResponse cores = new CourseResponse();
				cores.setCourseCode(course.getCourseCode());
				cores.setCourseName(course.getCourseName());
				cores.setCredit(course.getCredit());
				resList.add(cores);
			}
			return resList;
		}

	}

	@Override
	public List<StudentResponse> findStudentCourse(String studentId) {
		List<StudentCourse> stucList = studentDao.findAllByStudentId(studentId);
		List<StudentResponse> sresList = new ArrayList<>();
		for (StudentCourse studentCourse : stucList) {
			StudentResponse sres = new StudentResponse();
			sres.setStudentId(studentCourse.getStudentId());
			sres.setStudentName(studentCourse.getStudentName());
			sres.setCoursecode(studentCourse.getCourseCode());
			sres.setCoursename(studentCourse.getCourseName());
			sres.setCourseday(studentCourse.getCourseDay());
			sres.setCourseStart(studentCourse.getCourseStart());
			sres.setCourseEnd(studentCourse.getCourseEnd());
			sres.setCreddit(studentCourse.getCredit());
			sresList.add(sres);
		}

		return sresList;
	}

	@Override
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest) {
		int x = 0;
		AddCourseResponse adcr = new AddCourseResponse();
		StudentCourse studentCourse = new StudentCourse();
		Optional<Course> courseOp = courseDao.findById(addCourseRequest.getCoursecode());
		Course course = courseOp.orElse(null);
		if (courseOp == null || courseOp.isEmpty()) {
			adcr.setMessage("無此課程");
		}
		// 找Id對應的學生課程
		List<StudentCourse> scList = studentDao.findAllByStudentId(addCourseRequest.getStudentId());
		// 如果都沒有的話，新增資料
		if (scList == null || scList.isEmpty()) {
			studentCourse.setId(addCourseRequest.getId());
			studentCourse.setCourseCode(course.getCourseCode());
			studentCourse.setCourseName(course.getCourseName());
			studentCourse.setStudentName(addCourseRequest.getStudentName());
			studentCourse.setStudentId(addCourseRequest.getStudentId());
			studentCourse.setCourseDay(course.getCourseDay());
			studentCourse.setCourseStart(course.getCourseStart());
			studentCourse.setCourseEnd(course.getCourseEnd());
			studentCourse.setCredit(course.getCredit());

			// 如果有值存在的話，做檢查
		} else {
			for (StudentCourse sc : scList) {
				if (addCourseRequest.getId().equalsIgnoreCase(sc.getId())) {
					adcr.setMessage("重複!");
					return adcr;
				}
				int totalCredit = 0;
				studentCourse.setId(addCourseRequest.getId());
				studentCourse.setCourseCode(course.getCourseCode());
				studentCourse.setCourseName(course.getCourseName());
				studentCourse.setStudentName(addCourseRequest.getStudentName());
				studentCourse.setStudentId(addCourseRequest.getStudentId());
				studentCourse.setCourseDay(course.getCourseDay());
				studentCourse.setCourseStart(course.getCourseStart());
				studentCourse.setCourseEnd(course.getCourseEnd());
				studentCourse.setCredit(course.getCredit());
				totalCredit += course.getCredit();
				for (StudentCourse sc1 : scList) {

					totalCredit += sc1.getCredit();
					if (studentCourse.getCourseName().equalsIgnoreCase(sc1.getCourseName())) {
						adcr.setMessage("重複!");
						return adcr;
					}
					if (addCourseRequest.getCoursecode().equalsIgnoreCase(sc1.getCourseCode())) {
						adcr.setMessage("重複!");
						return adcr;
					}
					if (totalCredit > 10) {
						adcr.setMessage("學分不能大於10");
						return adcr;
					}
				}
				break;
			}
		}
		adcr.setCoursecode(course.getCourseCode());
		adcr.setStudentId(addCourseRequest.getStudentId());
		adcr.setMessage("加選成功");
		studentDao.save(studentCourse);
		return adcr;
	}
	
	@Override
	 public Course reviseCourse(String courseCode, String courseName, String courseDay, LocalTime courseStart,
	   LocalTime courseEnd, int credit) {
	  Course course = new Course();
	  Optional<Course> courseOp = courseDao.findById(courseCode);
	  // 判斷資料庫內容
	  if (courseOp.isPresent() || !StringUtils.hasText(courseCode)) {
	   course.setCourseCode(courseCode);
	   course.setCourseName(courseName);
	   course.setCourseDay(courseDay);
	   course.setCourseStart(courseStart);
	   course.setCourseEnd(courseEnd);
	   course.setCredit(credit);
	   courseDao.save(course);
	  }
	  return course;
	 }
}
