package interfaces;

import javax.ejb.Local;

import agent_center.AgentCenter;

@Local
public interface RestNodeLocal {

	public static String LOOKUP = "java:app/ATProjectJAR/RestNode!interfaces.RestNodeLocal";
	
	void postNode(AgentCenter ac);
		
	void deleteNode(String alias);
	
}