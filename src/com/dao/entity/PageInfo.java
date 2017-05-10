package com.dao.entity;

public class PageInfo {
	private String con;
	private int offset;
	private int size;
	public PageInfo(){}
	public PageInfo(String con, int offset, int size) {
		super();
		this.con = con;
		this.offset = offset;
		this.size = size;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
}
