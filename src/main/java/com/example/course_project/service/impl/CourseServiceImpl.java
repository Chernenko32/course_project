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
import com.example.course_project.vo.CourseRequest;

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
		//防呆
		if (!StringUtils.hasText(courseCode) || !StringUtils.hasText(courseName) || !StringUtils.hasText(courseDay)
				|| courseStart == null || courseEnd == null) {
			res.setMessage("課程內容錯誤");
			return res;
		}

		Optional<Course> optional = courseDao.findById(courseCode);
		if (optional.isPresent()) {
			res.setMessage("課程已存在  !");
			return res;
		}

		if (courseStart.isAfter(courseEnd) || courseEnd.isBefore(courseStart)) {
			res.setMessage("時間錯誤!");
			return res;
		}

		if (credit > 3 || credit < 0) {
			res.setMessage("學分錯誤!");
			return res;
		}
		
		List<String> checkDay = new ArrayList<>();
		checkDay.add("MON");
		checkDay.add("TUE");
		checkDay.add("WED");
		checkDay.add("THU");
		checkDay.add("FRI");
		checkDay.add("SAT");

		if (!checkDay.stream().anyMatch(day -> day.equalsIgnoreCase(courseDay))) {
			res.setMessage("日期錯誤!");
			return res;
		}
		;

		if (hasCourseCode(courseCode)) {
			res.setMessage("代碼錯誤!");
			return res;
		}
		;
		//設值,插進資料庫
		Course course = new Course();
		course.setCourseCode(courseCode);
		course.setCourseName(courseName);
		course.setCourseDay(courseDay);
		course.setCourseStart(courseStart);
		course.setCourseEnd(courseEnd);
		course.setCredit(credit);
		courseDao.save(course);
		//顯示
		res.setCourseCode(course.getCourseCode());
		res.setCourseName(course.getCourseName());
		res.setCourseDay(course.getCourseDay());
		res.setCourseStart(course.getCourseStart());
		res.setCourseEnd(course.getCourseEnd());
		res.setCredit(course.getCredit());
		
		res.setMessage("課程新增成功");
		return res;
	}

	@Override
	public CourseResponse deleteCourseById(CourseRequest req) {
		CourseResponse res = new CourseResponse();
		if (req.getCourseCode().isEmpty()) {
			res.setMessage("課程代碼錯誤");
			return res;
		}
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		if (!courseOp.isPresent()) {
			res.setMessage("課程不存在");
			return res;
		}
		if (courseOp.isPresent() || !StringUtils.hasText(req.getCourseCode())) {
			courseDao.deleteById(req.getCourseCode());
			res.setMessage("刪除成功!!");
		}
		
		return res;
	}

	@Override
	public CourseResponse getCourse(String courseCode, String courseName) {
		CourseResponse res = new CourseResponse();
		if (courseName == null) {
			if (!StringUtils.hasText(courseCode)) {
				res.setMessage("id錯誤");
				return res;
			}
			Optional<Course> optionalcourse = courseDao.findById(courseCode);
			Course course = optionalcourse.orElse(null);
			if (course == null) {
				res.setMessage("不存在");
				return res;
			} else {
				res.setCourseCode(course.getCourseCode());
				res.setCourseName(course.getCourseName());
				res.setCourseDay(course.getCourseDay());
				res.setCourseStart(course.getCourseStart());
				res.setCourseEnd(course.getCourseEnd());
				res.setCredit(course.getCredit());
			}

			return res;
		} else {
			if (!StringUtils.hasText(courseName)) {
				res.setMessage("課程名錯誤!");

				return res;
			}
			List<Course> listCourse = courseDao.findByCourseName(courseName);
			if (listCourse == null || listCourse.isEmpty()) {
				res.setMessage("課程不存在!");
				return res;
			}
			List<Course> courseList = new ArrayList<>();
			//用for迴圈把List型態轉成response型態
			for (Course course : listCourse) {
				Course cores = new Course();
				cores.setCourseCode(course.getCourseCode());
				cores.setCourseName(course.getCourseName());
				cores.setCourseDay(course.getCourseDay());
				cores.setCourseStart(course.getCourseStart());
				cores.setCourseEnd(course.getCourseEnd());
				cores.setCredit(course.getCredit());
				courseList.add(cores);
			}
			res.setCourseList(courseList);
			return res;
		}

	}

	@Override
	public StudentResponse findStudentCourse(String studentId) {
		StudentResponse studentResponse = new StudentResponse();
		if (studentId == null || studentId.isEmpty()) {
			studentResponse.setMessage("id錯誤!");
			return studentResponse;
		}
		List<StudentCourse> stucList = studentDao.findAllByStudentId(studentId);
		if (stucList.isEmpty()) {
			studentResponse.setMessage("id錯誤或尚未選課!");
			return studentResponse;
		}
		studentResponse.setStudentlist(stucList);
		return studentResponse;
	}

	@Override
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest) {

		StudentCourse studentCourseEntity = new StudentCourse();
		AddCourseResponse response = new AddCourseResponse();

		if (!StringUtils.hasText(addCourseRequest.getId())) {
			response.setMessage("id錯誤!");
			return response;
		}

		if (!StringUtils.hasText(addCourseRequest.getCoursecode())) {
			response.setMessage("課程代碼錯誤!");
			return response;
		}
		
		if (!StringUtils.hasText(addCourseRequest.getStudentId())) {
			response.setMessage("學生id錯誤!");
			return response;
		}

		if (!StringUtils.hasText(addCourseRequest.getStudentName())) {
			response.setMessage("學生名錯誤!");
			return response;
		}

		Optional<Course> courseOptional = courseDao.findById(addCourseRequest.getCoursecode());
		if (!courseOptional.isPresent()) {
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
				response.setMessage("課程名稱重複!");
				return response;
			}
			if (!studentCourse.getStudentName().equalsIgnoreCase(addCourseRequest.getStudentName())) {
				response.setMessage("學生id錯誤!");
				return response;
			}
			
			if (addCourseRequest.getCoursecode().equalsIgnoreCase(studentCourse.getCourseCode())) {
				response.setMessage("課程代碼重複!");
				return response;
			}
			if (totalCredit > 10) {
				response.setMessage("學分不能大於10");
				return response;
			}
			//防衝堂
			if (course.getCourseDay().equals(studentCourse.getCourseDay())) {
				boolean time1 = course.getCourseStart().isAfter(studentCourse.getCourseStart())
						&& course.getCourseStart().isBefore(studentCourse.getCourseEnd());
				boolean time2 = course.getCourseEnd().isAfter(studentCourse.getCourseStart())
						&& course.getCourseEnd().isBefore(studentCourse.getCourseEnd());
				boolean time3 = course.getCourseStart().isBefore(studentCourse.getCourseStart())
						&& course.getCourseEnd().isAfter(studentCourse.getCourseEnd());
				boolean time4 = course.getCourseStart().equals(studentCourse.getCourseStart())
						&& course.getCourseEnd().equals(studentCourse.getCourseEnd());
				if (time1 || time2 || time3 || time4) {
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
	public CourseResponse reviseCourse(CourseRequest req) {
		CourseResponse res = new CourseResponse();
		//防呆
		if (req.getCourseCode().isEmpty()) {
			res.setMessage("課程代碼錯誤");
			return res;
		}
		if (req.getCourseName().isEmpty()) {
			res.setMessage("課程名稱錯誤");
			return res;
		}
		if (req.getCourseDay().isEmpty()) {
			res.setMessage("日期錯誤");
			return res;
		}
		if (req.getCourseStart() == null) {
			res.setMessage("開始時間錯誤");
			return res;
		}
		if (req.getCourseEnd() == null) {
			res.setMessage("結束時間錯誤");
			return res;
		}
		if (req.getCredit() == null) {
			res.setMessage("學分錯誤");
			return res;
		}

		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		if (courseOp.isPresent()) {
			Course course = courseOp.get();
			course.setCourseCode(course.getCourseCode());
			course.setCourseName(req.getCourseName());
			course.setCourseDay(req.getCourseDay());
			course.setCourseStart(req.getCourseStart());
			course.setCourseEnd(req.getCourseEnd());
			course.setCredit(req.getCredit());
			courseDao.save(course);
			
			res.setCourseCode(course.getCourseCode());
			res.setCourseName(req.getCourseName());
			res.setCourseDay(req.getCourseDay());
			res.setCourseStart(req.getCourseStart());
			res.setCourseEnd(req.getCourseEnd());
			res.setCredit(req.getCredit());
			res.setMessage("課程更改成功!!");
			return res;
		}
		res.setMessage("無此課程代碼!!");
		return res;
	}

	@Override
	public boolean hasCourseCode(String courseCode) {
		List<Course> courseList = courseDao.findAll();
		return courseList.stream().anyMatch(item -> item.getCourseCode().equalsIgnoreCase(courseCode));
	}

	@Override
	public StudentResponse deleteCourse(String studentId, String courseCode) {
		StudentResponse studentResponse = new StudentResponse();
		if (studentId == null || studentId.isEmpty()) {
			studentResponse.setMessage("id錯誤!");
			return studentResponse;
		}

		List<StudentCourse> stucList = studentDao.findAllByStudentId(studentId);
		if (stucList.isEmpty()) {
			studentResponse.setMessage("無此學生");
			return studentResponse;
		}
		List<StudentCourse> studentCourse = studentDao.findByStudentId(studentId);
		for(StudentCourse item:studentCourse) {
			String code = item.getCourseCode();
			if (!code.equals(courseCode)) {
				studentResponse.setMessage("無此課程");
				continue;
				
			}else {
				studentDao.deleteBycourseCode(courseCode);
				studentResponse.setMessage("刪除成功");
				break;
			}
			
		}
		
		return studentResponse;
	}

}
