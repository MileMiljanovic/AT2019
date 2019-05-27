package utils;

import org.json.JSONObject;

import agent_center.AgentCenter;
import message.ACLMessage;
import models.AID;
import models.AgentType;

public class JsonUtils {
	
	public static String getACLMessageString(ACLMessage msg) {
		JSONObject obj = new JSONObject();
		obj.append("type", "acl_message");
		obj.append("data", new JSONObject(msg));
		return obj.toString();
	}

	public static String getAIDString(AID aid, boolean start) {
		JSONObject obj = new JSONObject();
		if (start)
			obj.append("type", "start_agent");
		else
			obj.append("type", "stop_agent");
		obj.append("data", new JSONObject(aid));
		return obj.toString();
	}

	public static String getAgentType(AgentType at, boolean start) {
		JSONObject obj = new JSONObject();
		if (start)
			obj.append("type", "add_agent_type");
		else
			obj.append("type", "remove_agent_type");
		obj.append("data", new JSONObject(at));
		return obj.toString();
	}

	public static String getNodeRequestType(String nodeRequest) {
		JSONObject obj = new JSONObject(nodeRequest);
		return obj.getString("type");
	}

	public static AgentCenter getNodeRequestSlaveAddres(String nodeRequest) {
		JSONObject obj = new JSONObject(nodeRequest);
		JSONObject jdata = obj.getJSONObject("data");
		AgentCenter ac = new AgentCenter();
		ac.setAddress(jdata.getString("address"));
		ac.setAlias(jdata.getString("alias"));
		return ac;
	}

}
