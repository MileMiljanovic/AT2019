package agent_manager;

import java.util.List;

import javax.ejb.Local;

import message.ACLMessage;
import models.AID;
import models.AgentType;
import agent.AgentCenter;

@Local
public interface AgentManagerLocal {

	public static final String LOOKUP = "java:global/ATProjectEAR/ATProjectJAR/AgentManager!agent_manager.AgentManagerLocal";

	void startInit(AgentCenter center);

	List<AID> getRunningAgents();

	List<AgentType> getAgentTypes();

	AgentType getAgentType(String name, String module);

	boolean msgToAgent(AID agent, ACLMessage msg);

	void startAgent(AID agent);

	void stopAgent(AID agent);

	void addAgentType(AgentType at);

	void deleteAgentType(AgentType at);

	void deleteTypesByNode(AgentCenter center);

}