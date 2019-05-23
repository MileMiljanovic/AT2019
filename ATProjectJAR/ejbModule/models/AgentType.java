package models;

import java.io.Serializable;

public class AgentType implements Serializable{


	private static final long serialVersionUID = 1L;
	private String name;
	private String module;

	public AgentType() {
		super();
	}

	public AgentType(String name, String module) {
		super();
		this.name = name;
		this.module = module;
	}
	
	public AgentType(String url) {
		String[] s = url.split("\\$");
		this.module = s[0];
		this.name = s[1];
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Override
	public String toString() {
		return module + "." + name;
	}
}
