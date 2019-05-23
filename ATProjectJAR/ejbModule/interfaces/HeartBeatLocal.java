package interfaces;

import javax.ejb.Local;

@Local
public interface HeartBeatLocal {
	
	public static String LOOKUP = "java:app/ATProjectJAR/HeartBeat!interfaces.HeartBeatLocal";
	
}
