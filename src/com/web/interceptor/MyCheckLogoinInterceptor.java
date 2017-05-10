package com.web.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dao.entity.User;
import com.dao.impl.UserMapper;

import Tool.ServiceTool;
import Tool.SysTool;

/**
 * 登录页面拦截器，
 * <br>
 * 1.负责登录前的用户信息查询、验证与失败的转向
 * <br>
 * 2.登录成功，新视图的属性添加
 * <br>
 * 3.打印登录信息
 * */

public class MyCheckLogoinInterceptor implements HandlerInterceptor{
	private final static Logger log=Logger.getLogger("logfile");
	private static User u;

	@Autowired
	private UserMapper mapper;

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handel, Exception e)
			throws Exception {
		SysTool.setUserInfo(req,u);
		SysTool.addLogoNum();
		req.getSession().removeAttribute("verCode");
		log.info("在线用户人数"+SysTool.getLogoUserNum());
		System.out.println("------------");
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handel, ModelAndView view)
			throws Exception {
		System.out.println("post:"+handel.getClass().getName()+"\t");
		req.getSession().setAttribute("username", u.getUsername());
		String logoType=req.getParameter("logoType");
		//		Enumeration<String> names = req.getParameterNames();
		//		while(names.hasMoreElements()){
		//			System.out.println(names.nextElement());
		//		}
		if(logoType!=null&&logoType.equals("manaher")){    //要添加user权限等级的判断
			if(u.getLevel()>=5){
				view.setViewName("adminMessage");
			}else{
				view.addObject("message", "sorry your level is low,so you con not went into the admin page!!");
			}
			
		}else{
			view.addObject("message", "logoin is succeful ,welcome to join us:");
		}
		log.info("user:"+u.getUsername()+"logoin into the server!"+new Date().toString());
		view.addObject("user", u.getUsername());
		System.out.println("转向视图名："+view.getViewName());
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handel) throws Exception {
		/*
		 * 检查用户是否存在
		 * */
		u=null;
		String username=req.getParameter("uname").trim();   
		String psw=req.getParameter("psw").trim();
		if(req.getParameter("remUser")!=null||req.getParameter("autoLogo")!=null){
			try {
				psw=ServiceTool.decrypt(psw, username);  //自动登录与记住密码解密
			} catch (Exception e) {
			}
			
		}
		u=new User(username, psw);
		u=mapper.selectByUser(ServiceTool.addEcodeUser(u));
		if(u!=null){
			if(u.getLevel()==0){
				log.info("there is a user need action");
				req.setAttribute("message", "user has not do  the activation, please do the action first");
				req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/doAction.jsp").forward(req, res);
				return false; 
			}else{
				String remUser=null;
				String autoLogo = null;
				try {
					remUser=req.getParameter("remUser").trim();
					autoLogo=req.getParameter("autoLogo").trim();
				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println("is remember the user: "+remUser);

				if(autoLogo!=null&& autoLogo.equals("on")){
					//自动登录信息封装
					Map<String, String> cookieMap=new HashMap<>();
					cookieMap.put("name", username);
					cookieMap.put("encode", ServiceTool.encrypt(psw, username));
					cookieMap.put("autologo", autoLogo);
					addInfoToCookie(cookieMap,7,res);
				}else if(remUser!=null&& remUser.equals("on")){
					//记住密码
					Map<String, String> cookieMap=new HashMap<>();
					cookieMap.put("name", username);
					cookieMap.put("encode", ServiceTool.encrypt(psw, username));
					cookieMap.put("remUser", remUser);
					addInfoToCookie(cookieMap,7,res);
				}else{
					removeCookie(res);
				}
				
				return true; 
			}
		}else{
			req.getSession().removeAttribute("username");
			req.setAttribute("message", "user logoin is fail");
			req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/message.jsp").forward(req, res);
			return false; 
		}
	}
	
	/***
	 * 删除用户存储的cookie
	 * @param res 
	 */
	private void removeCookie(HttpServletResponse res) {
		Map<String, String> cookieMap=new HashMap<>();
		cookieMap.put("username", null);
		cookieMap.put("password", null);
		cookieMap.put("remUser", null);
		addInfoToCookie(cookieMap, 0, res);
	}

	/**
	 * 将用户信息转化成cookie
	 * @param res 
	 * */
	private void addInfoToCookie(Map<String, String> map, int i, HttpServletResponse res) {
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key=it.next();
			res.addCookie(addCookie(key, map.get(key), i));
		}
	}

	private Cookie addCookie(String string, String con, int i) {
		Cookie cookie=new Cookie(string, con);
		cookie.setMaxAge(i*60*60*24);
		return cookie;
	}


}
