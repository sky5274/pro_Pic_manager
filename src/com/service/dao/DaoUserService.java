package com.service.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.entity.User;
import com.dao.entity.UserInfo;
import com.dao.impl.UserInfoMapper;
import com.dao.impl.UserMapper;

public class DaoUserService {
	@Autowired
	private UserMapper usermapper;
	@Autowired
	private UserInfoMapper infomapper;

	public UserMapper getUsermapper() {
		return usermapper;
	}

	public void setUsermapper(UserMapper usermapper) {
		this.usermapper = usermapper;
	}

	public UserInfoMapper getInfomapper() {
		return infomapper;
	}

	public void setInfomapper(UserInfoMapper infomapper) {
		this.infomapper = infomapper;
	}
	
	public boolean isAction(String user, String email){
		User u=usermapper.selectByName(user);
		if(u!=null){
			UserInfo info=infomapper.selectByPrimaryKey(u.getUserinfoid());
			if(info!=null){
				if(info.getEmail().equals(email)){
					u.setLevel(u.getLevel()+1);
					usermapper.updateByPrimaryKeySelective(u);
					System.out.println("用户已经激活,用户当前权限："+u.getLevel()+" 级");
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public boolean isUserCanReset(String user, String email){
		User u=usermapper.selectByName(user);
		if(u!=null){
			UserInfo info=infomapper.selectByPrimaryKey(u.getUserinfoid());
			if(info!=null){
				if(info.getEmail().equals(email)){
					System.out.println("用户已被激活，可以修改密码,用户当前权限："+u.getLevel()+" 级");
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	

	public boolean isHave(String name) {
		if(usermapper.selectByName(name)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean updateUser(User u){
		if(usermapper.updateByPrimaryKeySelective(u)>0){
			return true;
		}else{
			return false;
		}
	}
	
	public int getUserPrimaryKey(String name){
		return usermapper.selectByName(name).getId();
	}
}
