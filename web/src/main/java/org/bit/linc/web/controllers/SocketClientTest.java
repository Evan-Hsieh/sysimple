package org.bit.linc.web.controllers;

import java.net.URI;
import java.util.concurrent.Future;

import javax.jws.soap.SOAPBinding;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;


public class SocketClientTest {
	 public static void main(String[] args)
	    {
	        URI uri = URI.create("ws://localhost:3000/socket/plugin/");

	        WebSocketClient client = new WebSocketClient();
	        try
	        {
	            try
	            {
	                client.start();
	                // The socket that receives events
	                TestClientSocket socket = new TestClientSocket();
	                // Attempt Connect
	                Future<Session> fut = client.connect(socket,uri);
	                // Wait for Connect
	                Session session = fut.get();
	                // Send a message
	                session.getRemote().sendString("Hadoop-cluster,hadoop-plugin,");
	                // Close session
	               // session.close();
	            }
	            finally
	            {
	                //client.stop();
	            }
	        }
	        catch (Throwable t)
	        {
	            t.printStackTrace(System.err);
	        }
	    }
	
	
	

}

