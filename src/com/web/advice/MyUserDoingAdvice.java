package com.web.advice;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyUserDoingAdvice {
	private Logger log=Logger.getLogger("logfile");
	private long userLogoined=0;
	
	@Pointcut("execution(* com.web.control.userControl.do*(*, *))")
	public void Check(){}
	
	@Before("Check()")
	public void doingAdvice(){
		log.info("----doing------");
	}
	
	@After("Check()&&args(user,psw)")
	public void logoinedInfo(String user,String psw){
		log.info("-------->user:"+user+",has success logoined. and now there are userNum:"+(++userLogoined));
	}
	
	
	@Around("execution(* com.web.control.userControl.doRegist(*,*))")
	public Object judgeLogo(ProceedingJoinPoint pjp){
		log.info("regist is starting");
		Object obj=null;
		try {
			obj=pjp.proceed();
		} catch (Throwable e) {
			log.info("regist is fialed");
			e.printStackTrace();
		}
		log.info("regist is end");
		return obj;
	}
}
