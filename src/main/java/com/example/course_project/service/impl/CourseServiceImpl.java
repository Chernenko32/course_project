package com.example.course_project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_project.constants.ErrorMessage;
import com.example.course_project.constants.Week;
import com.example.course_project.entity.Course;
import com.example.course_project.entity.StudentCourse;
import com.example.course_project.repository.CourseDao;
import com.example.course_project.repository.StudentDao;
import com.example.course_project.service.ifs.CourseService;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseRequest;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentDao studentDao;

	@Override
	public CourseResponse createCourse(CourseRequest req) {
		// 防呆
		CourseResponse res = checkCourseInput(req);
		if (res.getMessage() == null) {
			return res;
		}
		// 設值,插進資料庫
		Course course = new Course(req.getCourseCode(), req.getCourseName(), req.getCourseDay(), req.getCourseStart(),
				req.getCourseEnd(), req.getCredit());
		courseDao.save(course);
		// 顯示
		res = new CourseResponse(course.getCourseCode(), course.getCourseName(), course.getCourseDay(),
				course.getCourseStart(), course.getCourseEnd(), course.getCredit(), ErrorMessage.SUCCESSFUL.getMessage());
		return res;
	}

	@Override
	public CourseResponse deleteCourseById(CourseRequest req) {
		CourseResponse res = new CourseResponse();
		// 防呆
		if (!StringUtils.hasText(req.getCourseCode())) {
			res.setMessage(ErrorMessage.COURSE_CODE_ERROR.getMessage());
			return res;
		}
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		// 判斷是否存在
		if (!courseOp.isPresent()) {
			res.setMessage(ErrorMessage.THIS_COURSE_IS_NOT_EXIST.getMessage());
			return res;
		}
		// 如果判斷存在,執行刪除
		courseDao.deleteById(req.getCourseCode());
		res.setMessage(ErrorMessage.SUCCESSFUL.getMessage());
		// 回傳
		return res;
	}

	@Override // 用課程代碼尋找課程
	public CourseResponse getCourse(String courseCode, String courseName) {
		// 判斷課程名稱是否為空 or null
		// 防呆
		CourseResponse res = checkCourseCode(courseCode);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}
		// 如果課程代碼為空,用課程名稱尋找課程
		// 防呆
		res = checkCourseName(courseName);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}
		return res;
	}

	@Override // 查詢學生已選課程
	public StudentResponse findStudentCourse(String studentId) {
		StudentResponse studentResponse = new StudentResponse();
		// 防呆
		if (!StringUtils.hasText(studentId)) {
			studentResponse.setMessage(ErrorMessage.STUDENTID_ERROR.getMessage());
			return studentResponse;
		}
		// 用List儲存取得已選課程資訊
		List<StudentCourse> stucList = studentDao.findAllByStudentId(studentId);
		if (stucList.isEmpty()) {
			studentResponse.setMessage(ErrorMessage.STUDENTID_REQUIRED_OR_COURSE_IS_EMPTY.getMessage());
			return studentResponse;
		}
		// 回傳
		studentResponse.setStudentlist(stucList);
		return studentResponse;
	}

	@Override
	public AddCourseResponse addCourse(AddCourseRequest addCourseRequest) {

		AddCourseResponse response = new AddCourseResponse();
		// 防呆
		response = checkCourseInput(addCourseRequest, response);
		//TODO
		// 用課程代碼找相應課程
		Optional<Course> courseOptional = courseDao.findById(addCourseRequest.getCoursecode());
		if (!courseOptional.isPresent()) {
			response.setMessage(ErrorMessage.THIS_COURSE_IS_NOT_EXIST.getMessage());
			return response;
		}

		Course course = courseOptional.get();

		int totalCredit = course.getCredit();

		// 尋找學生列表
		List<StudentCourse> studentCourseList = studentDao.findAllByStudentId(addCourseRequest.getStudentId());
		for (StudentCourse studentCourse : studentCourseList) {
			totalCredit += studentCourse.getCredit();
			// 檢查
			// TODO
			response = checkInputAndDBOfCourse(addCourseRequest, response, totalCredit, course, studentCourse);
			if (StringUtils.hasText(response.getMessage())) {
				return response;
			}
		}
		// 設值,存進資料庫
		StudentCourse studentCourseEntity = new StudentCourse(addCourseRequest.getId(), course.getCourseCode(),
				course.getCourseName(), addCourseRequest.getStudentName(), addCourseRequest.getStudentId(),
				course.getCourseDay(), course.getCourseStart(), course.getCourseEnd(), course.getCredit());
		// 儲存
		studentDao.save(studentCourseEntity);
		// 顯示
		response.setCoursecode(course.getCourseCode());
		response.setStudentId(addCourseRequest.getStudentId());
		response.setMessage(ErrorMessage.SUCCESSFUL.getMessage());
		return response;
	}

	// 修改課程資訊
	@Override
	public CourseResponse reviseCourse(CourseRequest req) {
		CourseResponse res = new CourseResponse();
		// 防呆
		res = chechCouseInput(req, res);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}
		// 用課程代碼尋找目標課程
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		// 如果尋找的課程存在
		if (courseOp.isPresent()) {
			// 設值,插進資料庫裡
			Course course = courseOp.get();
			course.setCourseCode(course.getCourseCode());
			course.setCourseName(req.getCourseName());
			course.setCourseDay(req.getCourseDay());
			course.setCourseStart(req.getCourseStart());
			course.setCourseEnd(req.getCourseEnd());
			course.setCredit(req.getCredit());
			courseDao.save(course);
			// 顯示
			res = new CourseResponse(course.getCourseCode(),req.getCourseName(),req.getCourseDay()
					,req.getCourseStart(),req.getCourseEnd(),req.getCredit(),ErrorMessage.SUCCESSFUL.getMessage());
			
			return res;
		}
		// 如果尋找的課程不存在,回傳錯誤訊息
		res.setMessage(ErrorMessage.THIS_COURSE_IS_NOT_EXIST.getMessage());
		return res;
	}

	// 自定義方法
	private boolean hasCourseCode(String courseCode) {
		List<Course> courseList = courseDao.findAll();
		return courseList.stream().anyMatch(item -> item.getCourseCode().equalsIgnoreCase(courseCode));
	}

	// 退選
	@Override
	public StudentResponse deleteCourse(String studentId, String courseCode) {
		StudentResponse studentResponse = new StudentResponse();
		// 防呆
		if (studentId == null || studentId.isEmpty()) {
			studentResponse.setMessage(ErrorMessage.STUDENTID_ERROR.getMessage());
			return studentResponse;
		}
		// 從資料庫尋找目標學生id
		List<StudentCourse> stucList = studentDao.findAllByStudentId(studentId);
		// 判斷
		if (stucList.isEmpty()) {
			studentResponse.setMessage(ErrorMessage.THIS_STUDENT_IS_NOT_EXIST.getMessage());
			return studentResponse;
		}

//		List<StudentCourse> deleteCourseList = new ArrayList<>();
		for (StudentCourse item : stucList) {
			String code = item.getCourseCode();
			// 判斷是否存在,存在就執行刪除
			if (code.equals(courseCode)) {
				studentDao.deleteByCourseCode(courseCode);
				studentResponse.setMessage(ErrorMessage.SUCCESSFUL.getMessage());
				break;
			}
		}
//		studentDao.deleteAll(deleteCourseList);
		// 回傳
		return studentResponse;
	}

	private CourseResponse checkCourseInput(CourseRequest req) {
		CourseResponse res = new CourseResponse();
		if (!StringUtils.hasText(req.getCourseCode()) || !StringUtils.hasText(req.getCourseName())
				|| !StringUtils.hasText(req.getCourseDay()) || req.getCourseStart() == null
				|| req.getCourseEnd() == null) {
			res.setMessage(ErrorMessage.COURSE_ERROR.getMessage());
			return res;
		}

		Optional<Course> optional = courseDao.findById(req.getCourseCode());
		if (optional.isPresent()) {
			res.setMessage(ErrorMessage.THIS_COURSE_IS_EXIST.getMessage());
			return res;
		}

		if (req.getCourseStart().isAfter(req.getCourseEnd()) || req.getCourseEnd().isBefore(req.getCourseStart())) {
			res.setMessage(ErrorMessage.TIME_ERROR.getMessage());
			return res;
		}

		if (req.getCredit() > 3 || req.getCredit() < 0) {
			res.setMessage(ErrorMessage.CREDIT_ERROR.getMessage());
			return res;
		}

//		List<String> checkDay = new ArrayList<>();
//		checkDay.add("MON");
//		checkDay.add("TUE");
//		checkDay.add("WED");
//		checkDay.add("THU");
//		checkDay.add("FRI");
//		checkDay.add("SAT");

		if (!Week.checkWeeek(req.getCourseDay())) {
			res.setMessage(ErrorMessage.DATE_ERROR.getMessage());
			return res;
		}
//		if (!checkDay.stream().anyMatch(day -> day.equalsIgnoreCase(req.getCourseDay()))) {
//			res.setMessage("日期錯誤!");
//			return res;
//		}

		if (hasCourseCode(req.getCourseCode())) {
			res.setMessage(ErrorMessage.CODE_ERROR.getMessage());
			return res;
		}
		return res;
	}

	private AddCourseResponse checkCourseInput(AddCourseRequest addCourseRequest, AddCourseResponse response) {

		if (!StringUtils.hasText(addCourseRequest.getId())) {
			response.setMessage(ErrorMessage.ID_EEROR.getMessage());
			return response;
		}

		if (!StringUtils.hasText(addCourseRequest.getCoursecode())) {
			response.setMessage(ErrorMessage.COURSE_CODE_ERROR.getMessage());
			return response;
		}

		if (!StringUtils.hasText(addCourseRequest.getStudentId())) {
			response.setMessage(ErrorMessage.STUDENTID_ERROR.getMessage());
			return response;
		}

		if (!StringUtils.hasText(addCourseRequest.getStudentName())) {
			response.setMessage(ErrorMessage.STUDENTNAME_ERROR.getMessage());
			return response;
		}
		return response;
	}

	private AddCourseResponse checkInputAndDBOfCourse(AddCourseRequest addCourseRequest, AddCourseResponse response,
			int totalCredit, Course course, StudentCourse studentCourse) {
		if (addCourseRequest.getId().equalsIgnoreCase(studentCourse.getId())) {
			response.setMessage(ErrorMessage.ID_IS_EXIST.getMessage());
			return response;
		}

		if (course.getCourseName().equalsIgnoreCase(studentCourse.getCourseName())) {
			response.setMessage(ErrorMessage.COURSENAME_IS_EXIST.getMessage());
			return response;
		}
		if (!studentCourse.getStudentName().equalsIgnoreCase(addCourseRequest.getStudentName())) {
			response.setMessage(ErrorMessage.STUDENTID_ERROR.getMessage());
			return response;
		}

		if (addCourseRequest.getCoursecode().equalsIgnoreCase(studentCourse.getCourseCode())) {
			response.setMessage(ErrorMessage.COURSEID_IS_EXIST.getMessage());
			return response;
		}
		if (totalCredit > 10) {
			response.setMessage(ErrorMessage.CREDITS_CANNOT_GREATER_THAN_10.getMessage());
			return response;
		}
		// 防衝堂
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
				response.setMessage(ErrorMessage.COURSE_TIME_OVERLAP.getMessage());
				return response;
			}
		}
		return response;
	}

	private CourseResponse chechCouseInput(CourseRequest req, CourseResponse res) {
		if (req.getCourseCode().isEmpty()) {
			res.setMessage(ErrorMessage.COURSE_CODE_ERROR.getMessage());
			return res;
		}
		if (req.getCourseName().isEmpty()) {
			res.setMessage(ErrorMessage.COURSENAME_ERROR.getMessage());
			return res;
		}
		if (req.getCourseDay().isEmpty()) {
			res.setMessage(ErrorMessage.DATE_ERROR.getMessage());
			return res;
		}
		if (req.getCourseStart() == null) {
			res.setMessage(ErrorMessage.START_TIME_ERROR.getMessage());
			return res;
		}
		if (req.getCourseEnd() == null) {
			res.setMessage(ErrorMessage.END_TIME_ERROR.getMessage());
			return res;
		}
		if (req.getCredit() == null) {
			res.setMessage(ErrorMessage.CREDIT_ERROR.getMessage());
			return res;
		}
		return res;
	}

	private CourseResponse checkCourseCode(String courseCode) {
		CourseResponse res = new CourseResponse();
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(ErrorMessage.ID_EEROR.getMessage());
			return res;
		}
		Optional<Course> optionalcourse = courseDao.findById(courseCode);
		Course course = optionalcourse.orElse(null);
		// 利用課程代碼判斷是否存在,如果不存在,回傳不存在
		if (course == null) {
			res.setMessage(ErrorMessage.NOT_EXIST.getMessage());
			return res;
			// 如果存在,取得課程資訊
		} else { // TODO
			return new CourseResponse(course.getCourseCode(), course.getCourseName(), course.getCourseDay(),
					course.getCourseStart(), course.getCourseEnd(), course.getCredit(),ErrorMessage.SUCCESSFUL.getMessage());

		}

	}

	private CourseResponse checkCourseName(String courseName) {
		CourseResponse res = new CourseResponse();
		if (!StringUtils.hasText(courseName)) {
			res.setMessage(ErrorMessage.COURSENAME_ERROR.getMessage());
			return res;
		}
		// 用List儲存取得的課程資訊
		List<Course> listCourse = courseDao.findByCourseName(courseName);
		if (listCourse == null || listCourse.isEmpty()) {
			res.setMessage(ErrorMessage.THIS_COURSE_IS_NOT_EXIST.getMessage());
			return res;
		}
		List<Course> courseList = new ArrayList<>();
		// 用for迴圈把List型態轉成response型態
		for (Course course : listCourse) {
			Course cores = new Course(course.getCourseCode(), course.getCourseName(), course.getCourseDay(),
					course.getCourseStart(), course.getCourseEnd(), course.getCredit());

			courseList.add(cores);
		}
		// 回傳
		res.setCourseList(courseList);
		return res;
	}
}
