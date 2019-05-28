package implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
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
import message.Performative;
import interfaces.RestMessageLocal;

@Stateless
public class RestMessage implements RestMessageLocal {

	@Override
	public void postMessages(ACLMessage msg) {
		// posalji acl poruku
		try {
			final Properties env = new Properties();
			Context context = new InitialContext(env);
			ConnectionFactory cf = (ConnectionFactory) context.lookup(MDBConsumer.REMOTE_FACTORY);
			final Queue queue = (Queue) context.lookup(MDBConsumer.MDB_CONSUMER_QUEUE);
			context.close();
			Connection connection = cf.createConnection();
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			
			ObjectMessage tmsg = session.createObjectMessage(msg);
			MessageProducer producer = session.createProducer(queue);
			producer.send(tmsg);	
			producer.close();
			connection.stop();
			connection.close();	
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getMessages() {
		// vrati listu performativa
		List<String> ret = new ArrayList<String>();
		for(Enum<Performative> e : Performative.values()) {
			ret.add(e.toString());
		}
		return ret;
	}

}