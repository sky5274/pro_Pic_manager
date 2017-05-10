package com.dao.entity;

public class TypeInfo {
	private String type;
	private String context;
	public TypeInfo(){}
	public TypeInfo(String type, String context) {
		super();
		this.type = type;
		this.context = context;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
}
