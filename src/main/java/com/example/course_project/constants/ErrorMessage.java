package com.example.course_project.constants;

public enum ErrorMessage {
	SUCCESSFUL("成功"),
	COURSE_CODE_ERROR("課程代碼錯誤"),
	COURSE_CODE_IS_EMPTY("課程不存在"),
	STUDENTID_ERROR("學生id錯誤"),
	STUDENTNAME_ERROR("學生名錯誤"),
	STUDENTID_REQUIRED_OR_COURSE_IS_EMPTY("id錯誤或尚未選課!"),
	THIS_COURSE_IS_NOT_EXIST("無此課程"),
	THIS_STUDENT_IS_NOT_EXIST("無此學生"),
	COURSE_ERROR("課程內容錯誤"),
	THIS_COURSE_IS_EXIST("課程已存在"),
	TIME_ERROR("時間錯誤"),
	CREDIT_ERROR("學分錯誤"),
	DATE_ERROR("日期錯誤!"),
	ID_EEROR("id錯誤"),
	ID_IS_EXIST("ID已存在"),
	COURSENAME_IS_EXIST("課程名稱重複"),
	COURSEID_IS_EXIST("課程代碼重複"),
	CREDITS_CANNOT_GREATER_THAN_10("學分不能大於10"),
	COURSE_TIME_OVERLAP("與現有課程時間重疊"),
	COURSENAME_ERROR("課程名稱錯誤"),
	START_TIME_ERROR("開始時間錯誤"),
	END_TIME_ERROR("結束時間錯誤"),
	NOT_EXIST("不存在"),
	CODE_ERROR("代碼錯誤!");

	private String message;
	
	private ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
