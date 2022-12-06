package com.example.course_project.constants;

public enum Week {
	MON("MON"),
	TUE("TUE"),
	WED("WED"),
	THU("THU"),
	FRI("FRI"),
	SAT("SAT");

	private String message;
	
	private Week(String message) {	
		this.message = message;
	}
	
	public static boolean checkWeeek(String courseDay) {
		for(Week item : values()) {
			if (item.getMessage().equals(courseDay)) {
				return true;
			}
		}
		return false;
	}
	//set用不到

	public String getMessage() {
		return message;
	}

}
