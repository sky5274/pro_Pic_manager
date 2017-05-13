package com.web.control;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dao.entity.User;
import com.service.dao.DaoUserService;

import Tool.ServiceTool;
import Tool.VerifyCodeUtils;

@Controller
public class userControl {
	@Autowired
	private DaoUserService service;
	
	/**
	 * 转到首页
	 * */
	@RequestMapping("/index")
	public ModelAndView transIndex(){
		ModelAndView view =new ModelAndView("index");
		return view;
	}
	
	/**
	 * 登录成功，转向message.jsp页面
	 * */
	@RequestMapping("/logoin")
	public String doLogoin(String name,String psw){
		return "message";
	}
	
	/**
	 * 登录转向，转向logoin.jsp 登录页面
	 * */
	@RequestMapping("/logo")
	public String getLogo(){
		return "logoin";
	}
	
	/**
	 * 用户激活，后转向message.jsp页面
	 * */
	@RequestMapping("/doaction")
	public String doAction(HttpServletRequest req,HttpServletResponse res){
		String user=req.getParameter("user");
		String email=req.getParameter("email");
			if(service.isAction(user,email)){
				req.getSession().setAttribute("username", user);
				req.setAttribute("message", "do the action is successful,welcome join us");
				return "message";
			}else{
				req.setAttribute("message", "do the action is failed,please replace logoin");
				return "message";
			}
	}
	
	/**
	 * 重置用户时用户的信息验证
	 * */
	@RequestMapping("/doValiteUser")
	public void doValiteUser(HttpServletResponse res,HttpServletRequest req){
		String name=req.getParameter("name");
		String email=req.getParameter("email");
		String recode=req.getParameter("recode");
		String message="";
		if(recode.equals(req.getSession().getAttribute("verCode"))){
			if(service.isUserCanReset(name, email)){
				req.getSession().setAttribute("resUser", name);
				message="success";
			}else{
				message="con_error";
			}
		}else{
			message="recode_error";
		}
		
		try {
			res.getWriter().write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doresPsw")
	public void doRestPSW(HttpServletResponse res,HttpServletRequest req){
		String message="";
		String name=(String) req.getSession().getAttribute("resUser");
		String psw=req.getParameter("passwd");
		User u=new User();
		u.setId(service.getUserPrimaryKey(name));
		u.setPassword(psw);
		u=ServiceTool.addEcodeUser(u);
		if(service.updateUser(u)){
			message="sucess";
		}else{
			message="error";
		}
		 req.getSession().removeAttribute("resUser");
		try {
			res.getWriter().write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 注册成功，转向message.jsp页面
	 * */
	@RequestMapping("/getRegister")
	public String doRegist(HttpServletRequest req,HttpServletResponse res){
		return "message";
	}
	
	/**
	 * 注册前检验用户昵称是否重复
	 * */
	@RequestMapping("/valiteName")
	public void valiteUserNameIsSame(HttpServletRequest req,HttpServletResponse res){
		String name=req.getParameter("nkname"); 
		String message="";
		if(service.isHave(name)){
			message="same";
		}else{
			message="not";
		}
		try {
			res.getWriter().println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/resetPsw")
	public String transToResPSW(){
		return "resetPsw";
	}
	
	/**
	 * 获取验证码图片
	 * */
	@RequestMapping("/authImage")
	public void autoImg(HttpServletRequest req,HttpServletResponse res){
		res.setHeader("Pragma", "No-cache"); 
        res.setHeader("Cache-Control", "no-cache"); 
        res.setDateHeader("Expires", 0); 
        res.setContentType("image/jpeg"); 
           
        //生成随机字串 
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        //存入会话session 
        HttpSession session = req.getSession(true); 
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase()); 
        //生成图片 
        int w = 100, h = 30; 
        try {
			VerifyCodeUtils.outputImage(w, h, res.getOutputStream(), verifyCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
   
	}

	public DaoUserService getService() {
		return service;
	}

	public void setService(DaoUserService service) {
		this.service = service;
	}
	
}
