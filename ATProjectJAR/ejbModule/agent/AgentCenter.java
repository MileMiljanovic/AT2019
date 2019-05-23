package agent;

import java.io.Serializable;

public class AgentCenter implements Serializable {

	private static final long serialVersionUID = 1L;
	private String address;
	private String alias;

	public AgentCenter() {
	}

	public AgentCenter(String address, String alias) {
		this.address = address;
		this.alias = alias;
	}

	public AgentCenter(String url) {
		String[] s = url.split("\\$");
		this.alias = s[0];
		this.address = s[1];
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
