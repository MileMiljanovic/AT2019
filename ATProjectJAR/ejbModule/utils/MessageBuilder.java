package utils;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import message.MDBConsumer;
import message.ACLMessage;

public class MessageBuilder {
	
	public static boolean sendACL(ACLMessage msg) {
		try {

			Context context = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) context.lookup(MDBConsumer.REMOTE_FACTORY);
			final Queue queue = (Queue) context.lookup(MDBConsumer.MDB_CONSUMER_QUEUE);
			context.close();
			Connection connection = cf.createConnection();
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();

			ObjectMessage tmsg = session.createObjectMessage((Serializable) msg);
			//tmsg.setJMSDeliveryTime(12000);
			MessageProducer producer = session.createProducer(queue);
			producer.setTimeToLive(12000);
			producer.send(tmsg);
			producer.close();
			connection.stop();
			connection.close();
			return true;
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
			return false;
		}
	}

}
