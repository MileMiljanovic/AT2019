package models;

import java.io.Serializable;

import agent_center.AgentCenter;

public class AID implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String name;
	private AgentCenter host;
	private AgentType type;

	public AID() {
		super();
		name = "";
		host = null;
		type = null;
	}

	public AID(String name, AgentCenter host, AgentType type) {
		super();
		this.name = name;
		this.host = host;
		this.type = type;
	}

	public AID(String url) {
		// ime_agenta$alias$address$type_name$type_module
		String[] s = url.split("\\$");
		this.name = s[0];
		this.host = new AgentCenter(s[2], s[1]);
		this.type = new AgentType(s[3], s[4]);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AgentType getType() {
		return type;
	}

	public void setType(AgentType type) {
		this.type = type;
	}

	public AgentCenter getHost() {
		return host;
	}

	public void setHost(AgentCenter host) {
		this.host = host;
	}

	@Override
	public String toString() {
		//TODO override host.toString()
		return name + "$" + type + "@" + host.getAlias();
	}
}