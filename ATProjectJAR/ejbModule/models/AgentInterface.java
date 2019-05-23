package models;

import javax.ejb.Local;

import message.ACLMessage;

@Local
public interface AgentInterface {

	public void handleMessage(ACLMessage poruka);
	
}
