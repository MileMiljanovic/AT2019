package interfaces;

import java.util.List;

import javax.ejb.Local;

import message.ACLMessage;

@Local
public interface RestMessageLocal {

	public static String LOOKUP = "java:app/ATProjectJAR/RestMessage!interfaces.RestMessageLocal";
	
	// posalji acl poruku
	void postMessages(ACLMessage msg);
	
	// vrati listu performativa
	List<String> getMessages();
	
}