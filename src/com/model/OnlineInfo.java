package com.model;

public class OnlineInfo {
	private String id;
	private String sessionId;
	private String state;
	private String host;
	private int level;
	private String user;
	
	public OnlineInfo(){}
	public OnlineInfo(String sessionId){
		this.sessionId = sessionId;
	}
	public OnlineInfo(String id, String sessionId, String state, int level, String user) {
		super();
		this.id = id;
		this.sessionId = sessionId;
		this.state = state;
		this.level = level;
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id==null?"":id;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId==null?"":sessionId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	@Override
	public String toString() {
		return "OnlineInfo [id=" + id + ", sessionId=" + sessionId + ", state=" + state + ", host=" + host + ", level="
				+ level + ", user=" + user + "]";
	}
}
