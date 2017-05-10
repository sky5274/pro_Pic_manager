package com.dao.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfo {
    private Integer id;

    private String username;

    private String sex;

    private String email;

    private Date brithday;

    private Date hiredate;

    private String department;

    private Double salary;
    
    public UserInfo(){}

    public UserInfo(Integer id, String username, String sex, String email, Date brithday, Date hiredate,
			String department, Double salary) {
		super();
		this.id = id;
		this.username = username;
		this.sex = sex;
		this.email = email;
		this.brithday = brithday;
		this.hiredate = hiredate;
		this.department = department;
		this.salary = salary;
	}

	public UserInfo(String name, String sex, String email, String brithday, String hiredate, String department,
			String salary) {
		DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
		this.username=name;
		this.sex=sex;
		this.email=email;
		this.department=department;
		this.salary=new Double(salary);
		try {
			this.brithday=df.parse(brithday);
			this.hiredate=df.parse(hiredate);
		} catch (ParseException e) {
			this.brithday=new Date();
			this.hiredate=new Date();
			e.printStackTrace();
		}
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", username=" + username + ", sex=" + sex + ", email=" + email + ", brithday="
				+ brithday + ", hiredate=" + hiredate + ", department=" + department + ", salary=" + salary + "]";
	}
    
    
}