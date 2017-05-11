package com.web.interceptor;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dao.entity.User;
import com.dao.entity.UserInfo;
import com.dao.impl.UserInfoMapper;
import com.dao.impl.UserMapper;

import Tool.ServiceTool;
import Tool.SysTool;


/**
 * 注册页面拦截器，<br>
 * 1.负责注册前的用户信息插入与插入失败的转向
 * <br>
 * 2.注册成，新视图的属性添加
 * <br>
 * 3.打印注册信息
 * */
public class MyDoRegistInterceptor implements HandlerInterceptor{
	private final static Logger log=Logger.getLogger("logfile");
	private User user;
	
	@Autowired
	private UserMapper usermapper;
	
	@Autowired
	private UserInfoMapper infoMapper;
	

	@SuppressWarnings("deprecation")
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handel, Exception e)
			throws Exception {
		SysTool.addRedistNum();
		SysTool.addLogoNum();
		req.getSession().removeAttribute("verCode");
		log.info(new Date().toLocaleString()+user.getUsername()+" regist sucessed");
		log.info(new Date().getDate()+"注册人数"+(SysTool.getRegistUserNum()));
		user=null;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handel, ModelAndView view)
			throws Exception {
		view.addObject("message", "congratration you regist successfully!!");
		view .addObject("user", user.getUsername());
		log.info("now user:"+user.getUsername()+"registed!! "+new Date().toString());
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handel) throws Exception {
		
		String nkname=req.getParameter("nkname");
		String name=req.getParameter("name");
		String psw=req.getParameter("psw");
		String email=req.getParameter("email");
		String sex=req.getParameter("sex");
		String brithday=req.getParameter("brithday");
		String hiredate=req.getParameter("hiredate");
		String department=req.getParameter("department");
		String salary=req.getParameter("salary");
		
		user=new User(nkname, psw);
		UserInfo info=new UserInfo( name, sex, email, brithday, hiredate, department, salary);
		try {
			infoMapper.insert(info);
			user.setUserinfoid(infoMapper.selectByUserInfo(info).getId());
			if(user.getUserinfoid()==null){   //没有查询到具体的userinfo
				doReistError(req,res);
				return false;
			}
			user.setLevel(0);
			user=ServiceTool.addEcodeUser(user);
			SysTool.setUserInfo(req,user);  //在控制端添加在线用户信息
			usermapper.insert(user);
			return true;
		} catch (Exception e) {
			usermapper.deleteByPrimaryKey(user.getId());  //删除用户
			doReistError(req,res);
			e.printStackTrace();
			return false;
		}
	}

	private void doReistError(HttpServletRequest req, HttpServletResponse res){
		req.setAttribute("message", "user regist is fail");
		try {
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(req, res);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UserMapper getUsermapper() {
		return usermapper;
	}

	public void setUsermapper(UserMapper usermapper) {
		this.usermapper = usermapper;
	}

	public UserInfoMapper getInfoMapper() {
		return infoMapper;
	}

	public void setInfoMapper(UserInfoMapper infoMapper) {
		this.infoMapper = infoMapper;
	}

}
