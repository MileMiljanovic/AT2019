package agent;

import java.util.ArrayList;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agent_manager.AgentManagerLocal;
import message.ACLMessage;
import message.Performative;
import models.AID;
import models.AgentClass;
import utils.MessageBuilder;


@Stateful
public class InitiatorAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage poruka) {
		switch (poruka.getPerformative()) {
		case request:
			startContractNet();
			break;
		case propose:
			handlePropose(poruka);
			break;
		case refuse:
			handleRefuse(poruka);
			break;
		case inform:
			handleInform(poruka);
			break;
		case failure:
			handleFailure(poruka);
			break;
		default:
			System.out.println("Unexpected message.");
		}
	}

	private void startContractNet() {
		ArrayList<AID> participants = getParticipants();
		if (participants.isEmpty()) {
			System.out.println("Error: No participants. Exiting now.");
			return;
		}

		ACLMessage cfpMsg = new ACLMessage();
		cfpMsg.setPerformative(Performative.cfp);
		cfpMsg.setReplyBy(System.currentTimeMillis() + 5000);
		AID[] niz = new AID[participants.size()];
		for (int i = 0; i < participants.size(); i++) {
			niz[i] = participants.get(i);
		}
		cfpMsg.setReceivers(niz);
		cfpMsg.setSender(Id);
		MessageBuilder.sendACL(cfpMsg);
	}

	private ArrayList<AID> getParticipants() {
		Context ctx;
		try {
			ctx = new InitialContext();
			AgentManagerLocal manager = (AgentManagerLocal) ctx.lookup(AgentManagerLocal.LOOKUP);
			ArrayList<AID> retVal = new ArrayList<>();
			for (AID id : manager.getRunningAgents()) {
				if (id.getType().getName().contains("ParticipantAgent")) {
					retVal.add(id);
				}
			}
			return retVal;
		} catch (NamingException e) {
			e.printStackTrace();
			return new ArrayList<AID>();
		}
	}

	private void handlePropose(ACLMessage msg) {
		System.out.println("Received proposal from agent " + msg.getSender());

		ACLMessage reply = new ACLMessage();
		reply.setReceivers(new AID[] { msg.getSender() });
		reply.setSender(Id);
		if (Math.random() < 0.3) {
			System.out.println("Rejecting proposal by agent " + msg.getSender());
			reply.setPerformative(Performative.reject_proposal);

		} else {
			System.out.println("Accepting proposal by agent " + msg.getSender());
			reply.setPerformative(Performative.accept_proposal);
		}

		MessageBuilder.sendACL(reply);
	}

	private void handleInform(ACLMessage msg) {
		System.out.println("Inform message from " + msg.getSender() + ": " + msg.getContent());
	}

	private void handleRefuse(ACLMessage msg) {
		System.out.println("Agent -> " + msg.getSender() + " refused.");
	}

	private void handleFailure(ACLMessage msg) {
		System.out.println("Agent -> " + msg.getSender() + " failed.");
	}

}
