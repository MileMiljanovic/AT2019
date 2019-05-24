package utils;

import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import models.AID;
import models.AgentType;
import agent.AgentCenter;

public class RestBuilder {
	
	public static void contactMaster(AgentCenter master, AgentCenter slave) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(master.getAddress() + "/ATProjectWAR");
        RestAPI rest = target.proxy(RestAPI.class);
        
        rest.connectNodes(slave);
	}
		
	public static List<AgentType> getSlaveAgentTypes(AgentCenter acc) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(acc.getAddress() + "/ATProjectWAR");
        RestAPI rest = target.proxy(RestAPI.class);
        
        List<AgentType> types = rest.getAgentTypes();
        return types;	
	}	
	
	public static void sendNewSlave(AgentCenter oldSlave, AgentCenter newSlave) {
		// saljemo nekom slaveu da se novi slave ukljucio u mrezu
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(oldSlave.getAddress() + "/ATProjectWAR");
        RestAPI rest = target.proxy(RestAPI.class);
        rest.connectNodes(newSlave);
	}
		
	public static void sendNewAgentTypes(AgentCenter oldSlave, List<AgentType> newSlaveAgentTypes) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(oldSlave.getAddress() + "/ATProjectWAR");
        RestAPI rest = target.proxy(RestAPI.class);   
        rest.newAgentTypes(newSlaveAgentTypes);
	}
	
	public static void sendNodesToSlave(AgentCenter newSlave, List<AgentCenter> nodes) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(newSlave.getAddress() + "/ATProjectWAR");
        RestAPI rest = target.proxy(RestAPI.class);
        
        for(AgentCenter a : nodes)
        	rest.connectNodes(a);
	}
	
	public static void sendRunningAgentsToSlave(AgentCenter newSlave, List<AID> runningAgents) {
		// master cvor slaje spisak pokrenutih agenata novom cvoru
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(newSlave.getAddress() + "/ATProjectWAR");
        RestAPI rest = target.proxy(RestAPI.class);
        
        rest.addRunningAgents(runningAgents);
	}
	
	public static boolean getHeartBeat(AgentCenter ac) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(ac.getAddress() + "/ATProjectWAR");
        RestAPI rest = target.proxy(RestAPI.class);
        
		return rest.heartBeat();
	}

}
