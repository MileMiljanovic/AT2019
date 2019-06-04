package message;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agent_manager.AgentManagerLocal;
import message.ACLMessage;
import models.AID;

/**
 * Message-Driven Bean implementation class for: MDBConsumer
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/mojQueue") })
public class MDBConsumer implements MessageListener {

	public static final String REMOTE_FACTORY = "java:jboss/exported/jms/RemoteConnectionFactory";
	public static final String MDB_CONSUMER_QUEUE = "java:jboss/exported/jms/queue/mojQueue";
	public static final String USERNAME = "guest";
	public static final String PASSWORD = "guest.guest.1";

	/**
	 * Default constructor.
	 */
	public MDBConsumer() {
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			ACLMessage msg = (ACLMessage) ((ObjectMessage) message).getObject();
			AID[] receivers = msg.getReceivers();
			System.out.println("MESSAGE ON QUEUE! - " + message);
			Context context = new InitialContext();
			AgentManagerLocal manager = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			for (AID a : receivers) {
				manager.msgToAgent(a, msg);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
