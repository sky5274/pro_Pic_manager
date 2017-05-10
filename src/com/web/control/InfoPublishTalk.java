package com.web.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.model.OnlineInfo;

import Tool.SysTool;
@ServerEndpoint("/socket/webSocketPushMessage")
public class InfoPublishTalk {
	private final static Logger log=Logger.getLogger("logfile");
	//接收到客户端的请求
	@OnMessage
	public void onMessage(String message, Session session)
			throws IOException, InterruptedException {
		System.out.println("Received: " + message);
		int num=10;
		Map<String, Object> map = AnalysisMessage(message);
		if(((String)map.get("request")).startsWith("get_server_info")){
			SysTool.setCanSend(true);
			//模拟推送数据
			while(num>0){
				Thread.sleep(2000);
				num--;
				if(SysTool.isCanSend()){
					try {
						session.getBasicRemote().sendText("serverInfo>"+getServerInfo());
					} catch (Exception e) {
						this.onClose();
					}
					
					SysTool.setCanSend(false);
				}
			}
		}else if(message==""){

		}else{
			session.getBasicRemote().sendText("error_message");
		}

	}
	//和客户端连接成功后	   
	@OnOpen
	public void onOpen() {
		log.info("Client connected");
	}
	//客户端关闭后	 
	@OnClose
	public void onClose() {
		log.info("Connection closed");
	}
	private String getServerInfo(){
		Map<String, OnlineInfo> info = SysTool.getOnLineInfo();
		List<Map<String, Object>> list = transToList(info);
		return JSON.toJSONString(list);
	}
	private List<Map<String, Object>> transToList(Map<String, OnlineInfo> info){
		Set<String> keys = info.keySet();
		List<Map<String, Object>> list=new ArrayList<>();
		for(String id:keys){
			Map<String, Object> map=new HashMap<>();
			map.put("obj", info.get(id));
			list.add(map);
		}
		return list;
	}
	
	
	private Map<String, Object> AnalysisMessage(String message){
		Map<String, Object> obj=new HashMap<>();
		 message=message.substring(1,message.length()-1);
		 String[] ss=message.split(",");
		 for(String s:ss){
			 String[] os=s.split(":");
			 if(os[0].replaceAll("\"", "").trim().indexOf("{")>=0&&os[0].replaceAll("\"", "").trim().indexOf("}")>os[0].replaceAll("\"", "").trim().indexOf("}")){
				 obj.put(os[0].replaceAll("\"", "").trim(), AnalysisMessage(os[1].replaceAll("\"", "").trim()));
			 }else{
				 obj.put(os[0].replaceAll("\"", "").trim(), os[1].replaceAll("\"", "").trim());
			 }
		 }
		return obj;
	}
}


