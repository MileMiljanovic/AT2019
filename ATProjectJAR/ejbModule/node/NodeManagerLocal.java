package node;

import java.util.List;

import javax.ejb.Local;

import agent_center.AgentCenter;
import models.AgentType;

@Local
public interface NodeManagerLocal {

	public static String LOOKUP = "java:app/ATProjectJAR/NodeManager!node.NodeManagerLocal";

	AgentCenter getMasterNode();
	AgentCenter getThisNode();
	List<AgentCenter> getSlaves();
	void deleteSlave(AgentCenter slave);
	void deleteSlave(String alias);
	void addSlave(AgentCenter slave, List<AgentType> slaveAgentTypes);
	void addSlave(AgentCenter slave);
	void addSlaveAgentTypes(AgentCenter slave, List<AgentType> slaveAgentTypes);
	
}