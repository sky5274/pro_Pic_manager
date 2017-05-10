package com.web.listener;

import java.util.logging.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyRequestSessionListener implements HttpSessionListener{
	private Logger log=Logger.getLogger("logfile");
	private static int i=0;

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		log.info(arg0.getSession().getId()+"登录服务器,当前在线客户数："+(++i));
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		log.info(arg0.getSession().getId()+"退出服务器");
		i--;
	}

}
