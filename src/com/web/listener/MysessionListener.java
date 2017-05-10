package com.web.listener;

import java.util.logging.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import Tool.SysTool;

public class MysessionListener implements HttpSessionListener{
	Logger log=Logger.getLogger("logfile");
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		log.info("now a clienter get into the server");
//		arg0.getSession().setAttribute("username", "lsd");
		SysTool.addSession(arg0.getSession().getId());
		SysTool.setCanSend(true);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		log.info("now a clienter leave the server");
		SysTool.removeSession(arg0.getSession().getId());
		SysTool.setCanSend(true);
	}

}
