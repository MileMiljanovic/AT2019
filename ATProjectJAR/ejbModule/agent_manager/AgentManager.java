package agent_manager;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Singleton;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import message.ACLMessage;
import models.AID;
import models.AgentClass;
import models.AgentType;
import agent.AgentCenter;
import node.NodeManagerLocal;
import interfaces.WebSocketLocal;
import utils.JsonUtils;

@Singleton
public class AgentManager implements AgentManagerLocal {

	private HashMap<AID, AgentClass> runningAgents;
	private HashMap<AgentCenter, ArrayList<AgentType>> agentTypes;

	@Override
	public void startInit(AgentCenter center) {
		runningAgents = new HashMap<AID, AgentClass>();
		initAgentTypes(center);
	}

	private void initAgentTypes(AgentCenter center) {
		agentTypes = new HashMap<>();

		final File basePackage = new File(
				AgentManagerLocal.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator
						+ "agents");

		ArrayList<AgentType> agentTypesList = processFile(basePackage);

		agentTypes.put(center, agentTypesList);
	}

	private ArrayList<AgentType> processFile(File f) {
		ArrayList<AgentType> types = new ArrayList<>();
		if (f.isDirectory()) {
			for (File file : f.listFiles()) {
				ArrayList<AgentType> tmp = processFile(file);
				types.addAll(tmp);
			}
		}

		if (f.isFile()) {
			File parent = f.getParentFile();
			String module = parent.getPath().substring(parent.getPath().lastIndexOf("agents"));
			module = module.replace(File.separatorChar, '.');
			String name = f.getName();
			name = name.substring(0, name.indexOf("."));
			AgentType at = new AgentType(name, module);
			types.add(at);
		}

		return types;
	}

	@Override
	public List<AID> getRunningAgents() {
		return new ArrayList<>(runningAgents.keySet());
	}

	@Override
	public List<AgentType> getAgentTypes() {
		ArrayList<AgentType> retVal = new ArrayList<>();
		for (AgentCenter key : agentTypes.keySet()) {
			retVal.addAll(agentTypes.get(key));
		}
		return retVal;
	}

	@Override
	public boolean msgToAgent(AID agent, ACLMessage msg) {
		AID proba = containsAgent(agent);
		AgentClass receiver = runningAgents.get(proba);
		if (receiver != null) {
			receiver.handleMessage(msg);

			try {
				Context context = new InitialContext();
				WebSocketLocal wsl = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
				wsl.sendMessage(JsonUtils.getACLMessageString(msg));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void startAgent(AID agent) {
		AID a = containsAgent(agent);
		if (a != null) {
			System.out.println("Vec postoji agent s tim identifikatorom!");
			return;
		}

		try {
			Object obj = Class.forName(agent.getType().toString()).newInstance();
			if (obj instanceof AgentClass) {
				((AgentClass) obj).setId(agent);
				runningAgents.put(agent, (AgentClass) obj);

				Context context = new InitialContext();
				WebSocketLocal wsl = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
				wsl.sendMessage(JsonUtils.getAIDString(agent, true));
			} else {
				System.out.println("Agent tipa " + agent.getType() + " se ne moze dodati u mapu!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopAgent(AID agent) {
		AID a = containsAgent(agent);
		if (a != null) {
			runningAgents.remove(a);
			Context context;
			try {
				context = new InitialContext();
				WebSocketLocal wsl = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
				wsl.sendMessage(JsonUtils.getAIDString(agent, false));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Ne postoji agent s tim identifikatorom!");
		}
	}

	@Override
	public AgentType getAgentType(String name, String module) {
		for (AgentCenter ac : agentTypes.keySet()) {
			for (AgentType at : agentTypes.get(ac)) {
				if (at.getName().equals(name) && at.getModule().equals(module)) {
					return at;
				}
			}
		}
		return null;
	}

	private AID containsAgent(AID key) {
		for (AID tmp : runningAgents.keySet()) {
			if (tmp.getHost().getAlias().equals(key.getHost().getAlias())
					&& tmp.getHost().getAddress().equals(key.getHost().getAddress())
					&& tmp.getName().equals(key.getName()) && tmp.getType().getName().equals(key.getType().getName())
					&& tmp.getType().getModule().equals(key.getType().getModule()))
				return tmp;
		}
		return null;
	}

	@Override
	public void addAgentType(AgentType at) {
		try {
			Context ctx = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) ctx.lookup(NodeManagerLocal.LOOKUP);
			AgentCenter center = nml.getThisNode();

			if (agentTypes.get(center) != null) {
				agentTypes.get(center).add(at);
			} else {
				ArrayList<AgentType> tmp = new ArrayList<AgentType>();
				tmp.add(at);
				agentTypes.put(center, tmp);
			}
			WebSocketLocal wsl = (WebSocketLocal) ctx.lookup(WebSocketLocal.LOOKUP);
			wsl.sendMessage(JsonUtils.getAgentType(at, true));
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteAgentType(AgentType at) {
		try {
			Context ctx = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) ctx.lookup(NodeManagerLocal.LOOKUP);
			AgentCenter center = nml.getThisNode();

			if (agentTypes.get(center) != null) {
				agentTypes.get(center).remove(at);
			}

			WebSocketLocal wsl = (WebSocketLocal) ctx.lookup(WebSocketLocal.LOOKUP);
			wsl.sendMessage(JsonUtils.getAgentType(at, false));
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTypesByNode(AgentCenter center) {
		agentTypes.remove(center);
	}

}
