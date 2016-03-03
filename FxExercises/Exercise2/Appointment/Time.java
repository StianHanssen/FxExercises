package Appointment;

public class Time {
	private Integer hour;
	private Integer minute;
	
	Time(String time) {
		if (isStrictFormated(time)) {
			String[] parts = time.split(":");
			hour = Integer.parseInt(parts[0]);
			minute = Integer.parseInt(parts[1]);
		}
	}
	
	public static boolean isFormated(String time) {
		int count = time.length() - time.replace(":", "").length();
		if (count != 1) {
			return false;
		}
		String[] parts = time.split(":");
		if (parts.length >= 1 && !parts[0].isEmpty()) {
			String hour = parts[0];
			if (!hour.matches("\\d{1,2}")) {
				return false;
			}
			int anHour = Integer.parseInt(hour);
			if (anHour < 0 || anHour > 23) {
				return false;
			}
		}
		if (parts.length >= 2 && !parts[1].isEmpty()) {
			String minute = parts[1];
			if (!minute.matches("\\d{1,2}")) {
				return false;
			}
			int aMinute = Integer.parseInt(minute);
			if (aMinute < 0 || aMinute > 59) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isStrictFormated(String time) {
		return time.matches("([01]*?[0-9]|2[0-3]):[0-5]*[0-9]");
	}
	
	public boolean hasTime() {
		return hour != null && minute != null;
	}
	
	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minute;
	}

	public int compare(Time aTime) {
		int hourNum = (int) Math.signum(hour - aTime.getHour());
		if (hourNum == 0) {
			return (int) Math.signum(minute - aTime.getMinutes());
		}
		return hourNum;
	}
	
	@Override
	public String toString() {
		return hour + ":" + minute;
	}
}
