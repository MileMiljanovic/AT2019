package interfaces;

import javax.ejb.Local;

import agent.AgentCenter;

@Local
public interface RestNodeLocal {

	public static String LOOKUP = "java:app/RestNode!interfaces.RestNodeLocal";
	
	void postNode(AgentCenter ac);
		
	void deleteNode(String alias);
	
}