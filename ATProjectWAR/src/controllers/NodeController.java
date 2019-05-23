package controllers;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import agent.AgentCenter;
import interfaces.RestNodeLocal;

@Path("/node")
public class NodeController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void connectNodes(AgentCenter request) {
		try {
			Context context = new InitialContext();
			RestNodeLocal rnl = (RestNodeLocal) context.lookup(RestNodeLocal.LOOKUP);
			rnl.postNode(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	@DELETE
	@Path("/{alias}")
	public void deleteNode(@PathParam("alias") String alias) {
		try {
			Context context = new InitialContext();
			RestNodeLocal rnl = (RestNodeLocal) context.lookup(RestNodeLocal.LOOKUP);
			rnl.deleteNode(alias);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// heartbeat protokol
	@GET
	public boolean heartBeat() {
		return true;
	}
	
}