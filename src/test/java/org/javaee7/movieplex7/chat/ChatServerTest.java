package org.javaee7.movieplex7.chat;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ChatServerTest {

	private List<Session> mockSessions = new ArrayList<>();
	private List<Session> mockSessions2 = new ArrayList<>();
	
	@Before
	public void setUp(){
		for (int i = 0; i < 6; i++){
			RemoteEndpoint.Basic remoteEndPoint =  Mockito.mock(RemoteEndpoint.Basic.class);
			Session mockSession = Mockito.mock(Session.class);
			when(mockSession.getBasicRemote()).thenReturn(remoteEndPoint);
			if(i % 2 == 0){
				mockSessions.add(mockSession);
			}else{
				mockSessions2.add(mockSession);
			}
		}
		
	}
	
	@After
	public void tearDown(){
		mockSessions.clear();
		mockSessions2.clear();
	}

	/**
	 * Tests that the sent message is sent to all clients
	 */
	@Test
	public void testMessage(){
		ChatServer s = new ChatServer();
		for(Session session : mockSessions){
			s.onOpen(session);
		}
		try {
			s.message("Test", mockSessions.get(0));
			for(Session session : mockSessions){
				verify(session.getBasicRemote()).sendText("Test");
			}
		} catch (IOException | EncodeException e) {
			fail("Couldn't send message");
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests that removed clients do are not being sent any messages
	 */
	@Test
	public void testDisconnectedClientNoMessage(){
		ChatServer s = new ChatServer();
		for(Session session : mockSessions){
			s.onOpen(session);
		}
		for(Session session : mockSessions){
			s.onClose(session);
		}
		try {
			s.message("Test", mockSessions.get(0));
			for(Session session : mockSessions){
				verify(session.getBasicRemote(), times(0)).sendText("Test");
			}
		} catch (IOException | EncodeException e) {
			fail("Couldn't send message");
			e.printStackTrace();
		}
	}
	
	/**
	 * Tests that multiple ChatServers use the same static peers list
	 */
	@Test
	public void testStaticMessage(){
		ChatServer s = new ChatServer();
		ChatServer s2 = new ChatServer();
		for(Session session : mockSessions){
			s.onOpen(session);
		}
		for(Session session : mockSessions2){
			s2.onOpen(session);
		}
		try {
			s.message("Test", mockSessions.get(0));
			for(Session session : mockSessions){
				verify(session.getBasicRemote()).sendText("Test");
			}
			for(Session session : mockSessions2){
				verify(session.getBasicRemote()).sendText("Test");
			}
		} catch (IOException | EncodeException e) {
			fail("Couldn't send message");
			e.printStackTrace();
		}
	}
}
