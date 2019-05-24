package websocket;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import interfaces.WebSocketLocal;

@ServerEndpoint("/websocket")
public class Websocket {
		
	public Websocket() { }
	
	@OnOpen
	public void onOpen(Session session) throws Exception {
		Context context = new InitialContext();
		WebSocketLocal ws = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
		ws.onOpen(session);
	}
	
	@OnMessage
	public void onMessage(Session session, String msg) throws Exception {
		Context context = new InitialContext();
		WebSocketLocal ws = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
		ws.onMessage(session, msg);
	}

	@OnClose
	public void onClose(Session session) throws Exception {
		Context context = new InitialContext();
		WebSocketLocal ws = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
		ws.onClose(session);
	} 
	
	@OnError
	public void onError(Session session, Throwable t) throws Exception {
		Context context = new InitialContext();
		WebSocketLocal ws = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
		ws.onError(session);;
	}
	
}
