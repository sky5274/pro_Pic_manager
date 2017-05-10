package com.web.contacttalk;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/socket/webSocketServer")
public class ScrollInfoHandler {
//接收到客户端的请求
	@OnMessage
	  public void onMessage(String message, Session session)
	    throws IOException, InterruptedException {
	    System.out.println("Received: " + message);
	    // Send the first message to the client
	    session.getBasicRemote().sendText("return params after handled: " +message);
	   
	    // Send 3 messages to the client every 5 seconds
	    //模拟推送数据
	    while(true){
	      Thread.sleep(2000);
	      session.getBasicRemote().
	        sendText("This is an intermediate server message. Count: "
	          + Math.random());
	    }
	  }
//和客户端连接成功后	   
	  @OnOpen
	  public void onOpen() {
	    System.out.println("Client connected");
	  }
//客户端关闭后	 
	  @OnClose
	  public void onClose() {
	    System.out.println("Connection closed");
	  }
	
}
