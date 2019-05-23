package controllers;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.AID;
import models.AgentType;
import interfaces.RestAgentsLocal;

@Path("/agents")
public class AgentsController {

	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentType> getAgentTypes() {
		try {
			Context context = new InitialContext();
			RestAgentsLocal ral = (RestAgentsLocal) context.lookup("java:app/AgentJAR/RestAgents!services.interfaces.RestAgentsLocal");
			return ral.getAgentsClasses();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@POST
	@Path("/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void newAgentTypes(List<AgentType> newTypes) {
		try {
			Context context = new InitialContext();
			RestAgentsLocal ral = (RestAgentsLocal) context.lookup("java:app/AgentJAR/RestAgents!services.interfaces.RestAgentsLocal");
			for(AgentType a : newTypes) {
				ral.addAgentType(a);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AID> getRunningAgents() {
		try {
			Context context = new InitialContext();
			RestAgentsLocal ral = (RestAgentsLocal) context.lookup("java:app/AgentJAR/RestAgents!services.interfaces.RestAgentsLocal");
			return ral.getAgentsRunning();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@POST
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public void addRunningAgents(List<AID> runningAgents) {
		try {
			Context context = new InitialContext();
			RestAgentsLocal ral = (RestAgentsLocal) context.lookup("java:app/AgentJAR/RestAgents!services.interfaces.RestAgentsLocal");
			for(AID a : runningAgents) {
				ral.addRunningAgent(a);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@PUT
	@Path("/running/{type}/{name}")
	public void startAgent(@PathParam("type") String type,@PathParam("name") String name) {
		//  pokreni agenta sa zadatim imenom
		try {
			Context context = new InitialContext();
			RestAgentsLocal ral = (RestAgentsLocal) context.lookup("java:app/AgentJAR/RestAgents!services.interfaces.RestAgentsLocal");
			ral.putAgentsRunning(new AgentType(type), name);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@DELETE
	@Path("/running/{aid}")
	public void stopAgent(@PathParam("aid") String aid) {
		// zaustavi odredjenog agenta
		try {
			Context context = new InitialContext();
			RestAgentsLocal ral = (RestAgentsLocal) context.lookup("java:app/AgentJAR/RestAgents!services.interfaces.RestAgentsLocal");
			ral.deleteAgentsRunning(new AID(aid));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}

