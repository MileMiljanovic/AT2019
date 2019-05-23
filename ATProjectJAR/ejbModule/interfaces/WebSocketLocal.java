package interfaces;

import javax.ejb.Local;
import javax.websocket.Session;

@Local
public interface WebSocketLocal {

	public static String LOOKUP = "java:app/ATProjectJAR/WebSocket!interfaces.WebSocketLocal";
	
	public void sendMessage(String msg) throws Exception;
	public void onOpen(Session session) throws Exception;
	public void onMessage(Session session, String msg) throws Exception;
	public void onClose(Session session) throws Exception;
	public void onError(Session session) throws Exception;
	
}