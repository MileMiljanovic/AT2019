package agent;

import javax.ejb.Stateful;

import message.ACLMessage;
import message.Performative;
import models.AID;
import models.AgentClass;
import utils.MessageBuilder;

@Stateful
public class PongAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage poruka) {
		System.out.println("Message received from Ping " + poruka.getContent());
		if (poruka.getPerformative() == Performative.request) {
			ACLMessage response = new ACLMessage();
			response.setReceivers(new AID[] { poruka.getSender() });
			response.setPerformative(Performative.inform);
			response.setContent("Reply to message received from Ping " + poruka.getSender());
			response.setSender(Id);
			MessageBuilder.sendACL(response);
		}

	}

}
