package implementation;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agent_center.AgentCenter;
import agent_manager.AgentManagerLocal;
import models.AID;
import models.AgentType;
import node.NodeManagerLocal;
import interfaces.RestAgentsLocal;

@Stateless
public class RestAgents implements RestAgentsLocal {

	@Override
	public List<AgentType> getAgentsClasses() {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			return aml.getAgentTypes();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AID> getAgentsRunning() {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			return aml.getRunningAgents();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void putAgentsRunning(AgentType type, String name) {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
			AgentCenter ac = nml.getThisNode();
			
			if(type != null) {
				AID aid = new AID(name, ac, type);
				aml.startAgent(aid);	
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteAgentsRunning(AID aid) {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			aml.stopAgent(aid);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


}
