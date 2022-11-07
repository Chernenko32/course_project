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

        StudentCourse studentCourseEntity = new StudentCourse();
        AddCourseResponse response = new AddCourseResponse();
        Optional<Course> courseOptional = courseDao.findById(addCourseRequest.getCoursecode());
        if (courseOptional.isPresent()) {
            response.setMessage("無此課程");
        }
        Course course = courseOptional.get();

        int totalCredit = course.getCredit();
        // 找Id對應的學生課程
        List<StudentCourse> studentCourseList = studentDao.findAllByStudentId(addCourseRequest.getStudentId());
        for (StudentCourse studentCourse : studentCourseList) {
            totalCredit += studentCourse.getCredit();
            if (addCourseRequest.getId().equalsIgnoreCase(studentCourse.getId())) {
                response.setMessage("id重複!");
                return response;
            }
            
            if (course.getCourseName().equalsIgnoreCase(studentCourse.getCourseName())) {
                response.setMessage("名稱重複!");
                return response;
            }
            if (addCourseRequest.getCoursecode().equalsIgnoreCase(studentCourse.getCourseCode())) {
                response.setMessage("代碼重複!");
                return response;
            }
            if (totalCredit > 10) {
                response.setMessage("學分不能大於10");
                return response;
            }
            if(course.getCourseDay().equals(studentCourse.getCourseDay())) {
            	boolean time1 = course.getCourseStart().isAfter(studentCourse.getCourseStart())&&course.getCourseStart().isBefore(studentCourse.getCourseEnd());
            	boolean time2 = course.getCourseEnd().isAfter(studentCourse.getCourseStart())&&course.getCourseEnd().isBefore(studentCourse.getCourseEnd());
            	boolean time3 = course.getCourseStart().isBefore(studentCourse.getCourseStart())&&course.getCourseEnd().isAfter(studentCourse.getCourseEnd());
            	boolean time4 = course.getCourseStart().equals(studentCourse.getCourseStart())&&course.getCourseEnd().equals(studentCourse.getCourseEnd());
            	if(time1||time2||time3||time4) {
            		response.setMessage("與現有課程時間重疊!");
            		return response;
            	}
            }
        }
        studentCourseEntity.setId(addCourseRequest.getId());
        studentCourseEntity.setCourseCode(course.getCourseCode());
        studentCourseEntity.setCourseName(course.getCourseName());
        studentCourseEntity.setStudentName(addCourseRequest.getStudentName());
        studentCourseEntity.setStudentId(addCourseRequest.getStudentId());
        studentCourseEntity.setCourseDay(course.getCourseDay());
        studentCourseEntity.setCourseStart(course.getCourseStart());
        studentCourseEntity.setCourseEnd(course.getCourseEnd());
        studentCourseEntity.setCredit(course.getCredit());
        studentDao.save(studentCourseEntity);

        response.setCoursecode(course.getCourseCode());
        response.setStudentId(addCourseRequest.getStudentId());
        response.setMessage("加選成功");
        return response;
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
