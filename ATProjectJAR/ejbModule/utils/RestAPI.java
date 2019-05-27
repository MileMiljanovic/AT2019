package utils;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import agent_center.AgentCenter;
import message.ACLMessage;
import models.AID;
import models.AgentType;

public interface RestAPI {

	@GET
	@Path("/agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<AgentType> getAgentTypes();
	
	@POST
	@Path("/agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void newAgentTypes(List<AgentType> newTypes);
	
	@GET
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<AID> getRunningAgents();
	
	@POST
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addRunningAgents(List<AID> runningAgents);
	
//	@PUT
//	@Path("/agents/running/{type}/{name}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void startAgent(@PathParam("type") String type,@PathParam("name") String name);
//	
//	@DELETE
//	@Path("/agents/running/{aid}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void stopAgent(@PathParam("aid") String aid);
//	
	@POST
	@Path("/messages")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMessage(ACLMessage msg);

//	@GET
//	@Path("/messages")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public List<String> getPerformatives();
//	
	@POST
	@Path("/node")
	@Consumes(MediaType.APPLICATION_JSON)
	public void connectNodes(AgentCenter request);
	
	@DELETE
	@Path("/node/{alias}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteNode(@PathParam("alias") String alias);
	
	@GET
	@Path("/node")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean heartBeat();
	
}
