package implementation;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.websocket.Session;

import interfaces.WebSocketLocal;

@Singleton
public class WebSocket implements WebSocketLocal {

	private static List<Session> sessions = new ArrayList<Session>();

	@Override
	public void sendMessage(String msg) throws Exception {
		for (Session s : sessions) {
			s.getAsyncRemote().sendText(msg);
		}
	}

	@Override
	public void onOpen(Session session) throws Exception {
		if (!sessions.contains(session))
			sessions.add(session);
	}

	@Override
	public void onMessage(Session session, String msg) throws Exception {

	}

	@Override
	public void onClose(Session session) throws Exception {
		if (sessions.contains(session))
			sessions.remove(session);
	}

	@Override
	public void onError(Session session) throws Exception {
		System.out.println("Error in websocket");
		if (sessions.contains(session))
			sessions.remove(session);
	}

}
