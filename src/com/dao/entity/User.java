package com.dao.entity;

public class User {
    private Integer id;

    private String username;

    private String password;

    private Integer userinfoid;

    private Integer level;
    public User(){}
    public User(String username2, String psw) {
		this.username=username2;
		this.password=psw;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getUserinfoid() {
        return userinfoid;
    }

    public void setUserinfoid(Integer userinfoid) {
        this.userinfoid = userinfoid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", userinfoid=" + userinfoid
				+ ", level=" + level + "]";
	}
}