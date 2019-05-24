package node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agent_manager.AgentManagerLocal;
import models.AgentType;
import agent.AgentCenter;
import interfaces.HeartBeatLocal;
import utils.RestBuilder;

@Singleton
@Startup
public class NodeManager implements NodeManagerLocal {

	private List<AgentCenter> nodes;
	private AgentCenter masterNode;
	private AgentCenter thisNode;

	public NodeManager() {
		nodes = new ArrayList<AgentCenter>();
	}

	@PostConstruct
	public void nodeInit() {
		setAgentCentre();
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			aml.startInit(getThisNode());
			
			//HeartBeatLocal hbl = (HeartBeatLocal) context.lookup(HeartBeatLocal.LOOKUP);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		if (!masterNode.getAddress().equals(thisNode.getAddress())) {
			RestBuilder.contactMaster(masterNode, thisNode);
		}
		
	}

	private void setAgentCentre() {
		final File configFile = new File(
				AgentManagerLocal.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator
						+ "META-INF" + File.separator + "node_config.txt");
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(configFile));

			String masterHost = br.readLine();
			String thisHost = br.readLine();
			this.masterNode = new AgentCenter(masterHost);
			this.thisNode = new AgentCenter(thisHost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public AgentCenter getMasterNode() {
		return masterNode;
	}

	@Override
	public AgentCenter getThisNode() {
		return thisNode;
	}

	@Override
	public List<AgentCenter> getSlaves() {
		return nodes;
	}

	@Override
	public void deleteSlave(AgentCenter slave) {
		try {
			Context ctx = new InitialContext();
			AgentManagerLocal agentManager = (AgentManagerLocal) ctx.lookup(AgentManagerLocal.LOOKUP);
			agentManager.deleteTypesByNode(slave);
			nodes.remove(slave);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addSlave(AgentCenter slave, List<AgentType> slaveAgentTypes) {
		this.nodes.add(slave);
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			for (AgentType a : slaveAgentTypes) {
				aml.addAgentType(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addSlave(AgentCenter slave) {
		this.nodes.add(slave);
	}

	@Override
	public void addSlaveAgentTypes(AgentCenter slave, List<AgentType> slaveAgentTypes) {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			for (AgentType a : slaveAgentTypes) {
				aml.addAgentType(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteSlave(String alias) {
		for(AgentCenter a : this.nodes) {
			if(a.getAlias().equals(alias))
				this.nodes.remove(a);
		}
		
	}
	
}

