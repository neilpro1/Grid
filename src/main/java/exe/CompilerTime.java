package exe;

public abstract class CompilerTime {

	private static final String SEC = "s";
	private static final String MIN = "m";
	private static final String HOUR = "h";
	private static final String DAY = "d";
	private static final String MON = "M";
	private static final String YEAR = "y";
	
	
	public static long getMilisec(String interval) {
		long time = -1;
		if(interval.contains(SEC)) {
			time = Long.parseLong(interval.split(SEC)[0]);
		}else if(interval.contains(MIN)) {
			time = Long.parseLong(interval.split(MIN)[0]) * 60;
		}else if(interval.contains(HOUR)) {
			time = Long.parseLong(interval.split(HOUR)[0]) * 60 * 60;
		}else if(interval.contains(DAY)) {
			time = Long.parseLong(interval.split(DAY)[0]) * 60 * 60 * 24;
		}else if(interval.contains(MON)) {
			time = Long.parseLong(interval.split(MON)[0]) * 60 * 60 * 24 * 30;
		}else if(interval.contains(YEAR)) {
			time = Long.parseLong(interval.split(YEAR)[0]) * 60 * 60 * 24 * 30 * 365;
		}
		return time * 1000;
	}

}
