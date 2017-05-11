package com.web.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.dao.entity.ClassInfo;
import com.dao.entity.Picture;
import com.dao.entity.ThemeInfo;
import com.dao.entity.User;
import com.dao.entity.UserInfo;
import com.dao.impl.ClassInfoMapper;
import com.dao.impl.ThemeInfoMapper;
import com.dao.impl.UserInfoMapper;
import com.dao.impl.UserMapper;
import com.model.OnlineInfo;
import com.service.dao.DaoService;

import Tool.ServiceTool;
import Tool.SysTool;

@Controller
public class AdminControl {

	@Autowired
	private ClassInfoMapper mapperByClass;
	@Autowired
	private ThemeInfoMapper mapperByTheme;
	@Autowired
	private UserMapper mapperUser;
	@Autowired
	private UserInfoMapper mapperUserInfo;
	@Autowired
	private DaoService service;

	@RequestMapping("/getPicByClass")
	public void queryPicByClass(HttpServletRequest req,HttpServletResponse res){
		List<ClassInfo> list=mapperByClass.selectInfo();
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(JSON.toJSONString(list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/getPicByTheme")
	public void queryPicByTheme(HttpServletRequest req,HttpServletResponse res){
		List<ClassInfo> list=mapperByClass.selectInfo();
		List<ThemeInfo> list1=mapperByTheme.selectInfo();

		List<Map<String, Object>> map = transtoList(list,list1);
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(JSON.toJSONString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/getPicInfoByTheme")
	public void queryPicInfoByTheme(HttpServletRequest req,HttpServletResponse res){
		List<ClassInfo> list=mapperByClass.selectInfo();
		List<ThemeInfo> list1=mapperByTheme.selectInfo();

		Map<String, List<String>> map = getInfoFromList(list,list1);
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(JSON.toJSONString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/queryPicByTheme")
	public void queryAllPicInfoByTheme(HttpServletRequest req,HttpServletResponse res){
		String theme=req.getParameter("theme");
		if(theme!=""||theme!=null){
			List<Map<String, String>> list = service.transToPicMapList(service.doSearchByTheme(theme));
			res.setCharacterEncoding("UTF-8");
			try {
				res.getWriter().write(JSON.toJSONString(list));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *更新them或clazz
	 * */
	@RequestMapping("/updataThOrCl")
	public void updateDataByTHorCl(HttpServletRequest req,HttpServletResponse res){
		String name=req.getParameter("name");
		String val=req.getParameter("val");
		String tar=req.getParameter("tar");
		//System.out.println(name+":"+val+":"+tar);
		String info="success";
		if(name.equals("clazz")){
			if(service.updateClass(val,tar)){
				info="success";
			}else{
				info="failed";
			}
		}else if(name.equals("theme")){
			if(service.updateTheme(val,tar)){
				info="success";
			}else{
				info="failed";
			}
		}else{
			info="args is erro";
		}
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/updataPicture")
	public void updateDataPicture(HttpServletRequest req,HttpServletResponse res){
		Enumeration<String> names = req.getParameterNames();
		Map<String, String> map=new HashMap<>();
		while(names.hasMoreElements()){
			String name=names.nextElement();
			//System.out.println(name+"------"+req.getParameter(name));
			map.put(name, req.getParameter(name));
		}
		Picture pic = transToPicture(map);
		String info="test";
		if(service.updatePic(pic)){
			info="success";
		}else{
			info="failed";
		}
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/getOnlineInfo")
	public void getOnlineInfo(HttpServletRequest req,HttpServletResponse res){
		int allUSERnum=SysTool.getOnLineInfo().size();
		int logoUsernum=SysTool.getLogoUserNum();
		int registUsernum=SysTool.getRegistUserNum();
		int havingUaernum=mapperUser.selectALL().size();
		Map<String, Object>map1=new HashMap<>();
		Map<String, Object>map2=new HashMap<>();
		Map<String, Object>map3=new HashMap<>();
		Map<String, Object>map4=new HashMap<>();
		map1.put("title", "在线人数");
		map1.put("value", allUSERnum);
		map2.put("title", "在线登录人数");
		map2.put("value", logoUsernum);
		map3.put("title", "今天注册人数");
		map3.put("value", registUsernum);
		map4.put("title", "已有注册人数");
		map4.put("value", havingUaernum);
		List<Map<String, Object>> list=new ArrayList<>();
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(JSON.toJSONString(list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/gerServerInfo")
	public void getServerInfo(HttpServletRequest req,HttpServletResponse res){
		Map<String, OnlineInfo> info = SysTool.getOnLineInfo();
		List<Map<String, Object>> list = transToList(info);
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(JSON.toJSONString(list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/getUserInfo")
	public void getUserInfo(HttpServletRequest req,HttpServletResponse res){
		String con="%"+req.getParameter("con")+"%";
		int type=Integer.parseInt(req.getParameter("type"));
		List<User> u_list=new ArrayList<>();
		switch (type) {
		case 1:
			u_list=mapperUser.selectVisitorRangeByName(con);
			break;
		case 2:
			u_list=mapperUser.selectManagerRangeByName(con);
			break;

		default:
			u_list=mapperUser.selectUserRangeByName(con);
			break;
		}
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(JSON.toJSONString(getInfoList(u_list)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/updateLevel")
	public void upateUserLevel(HttpServletRequest req,HttpServletResponse res){
		String id=req.getParameter("id");
		String level=req.getParameter("level");
		String name=req.getParameter("name");
		String message="";
		try {
			int i=Integer.parseInt(id);
			int l=Integer.parseInt(level);
			if(mapperUser.selectByPrimaryKey(i).getUsername().equals(name)){
				User u=new User();
				u.setId(i);
				u.setLevel(l);
				if(mapperUser.updateByPrimaryKeySelective(u)>0){
					message="success";
				}else{
					message="error_updata";
				}
			};
		} catch (Exception e) {
			message="args_error";
		}

		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(JSON.toJSONString(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/update/UserandUserInfo")
	public void updateUserAndUserInfo(HttpServletRequest req,HttpServletResponse res){
		String message="";
		DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
		User u=new User();
		u.setId(Integer.parseInt(req.getParameter("userid")));
		u.setUsername(req.getParameter("username"));
		u.setLevel(Integer.parseInt(req.getParameter("userlevel")));

		UserInfo info=new UserInfo();
		info.setUsername(req.getParameter("name"));
		info.setSex(req.getParameter("sex"));
		info.setEmail(req.getParameter("email"));
		info.setDepartment(req.getParameter("department"));
		info.setSalary(Double.parseDouble(req.getParameter("salary")));
		info.setId(Integer.parseInt(req.getParameter("infoId")));
		try {
			/*
			 *有缺陷，多条数据提交出现异常，无法事务回滚 
			 *初始设计表有缺陷，
			 *目前比较理想的设计方案：设计一张大表，并将之分成两个view，查询时查view,更新插入table
			 * */
			info.setHiredate(df.parse(req.getParameter("brithday")));
			info.setHiredate(df.parse(req.getParameter("hiredate")));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			mapperUser.updateByPrimaryKeySelective(u);
			mapperUserInfo.updateByPrimaryKeySelective(info);
			message="success";
		} catch (Exception e) {
			message="error";
			e.printStackTrace();
		}
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/update/ManagerRegist")
	public void updateRegist_M(HttpServletRequest req,HttpServletResponse res){
		String message="";
		DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
		String username=req.getParameter("nkname");
		String psw=ServiceTool.OnSecreatKey(req.getParameter("psw")==null?"1":req.getParameter("psw"));
		int level=Integer.parseInt(req.getParameter("level"));
		String email=req.getParameter("email");
		String name=req.getParameter("name");
		String sex=req.getParameter("sex");
		String department=req.getParameter("department");
		String salary=req.getParameter("salary");
		String brithday=req.getParameter("brithday");
		String hiredate=req.getParameter("hiredate");
		UserInfo info=new UserInfo();
		info.setUsername(name);
		info.setEmail(email);
		info.setSalary(Double.parseDouble(salary));
		info.setDepartment(department);
		info.setSex(sex);
		try {
			info.setBrithday(df.parse(brithday));
			info.setHiredate(df.parse(hiredate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
		UserInfo infos=mapperUserInfo.selectByUserInfo(info);
		if(infos==null){
			if(mapperUserInfo.insert(info)>0){
				User u=new User();
				u.setLevel(level);
				u.setUsername(username);
				u.setPassword(psw);
				System.out.println(mapperUserInfo.selectByUserInfo(info));
				i=mapperUserInfo.selectByUserInfo(info).getId();
				u.setUserinfoid(i);
				if(mapperUser.insert(u)>0){
					message="success";
				}else{
					mapperUserInfo.deleteByPrimaryKey(i);
					message="error_insert_user";
				}
			}else{
				message="error_insert_userinfo";
			}
		}else{
			message="error_user_ishave";
		}
		System.out.println(message);
		res.setCharacterEncoding("UTF-8");
		try {
			res.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	/**
	 * 获取查询的用户的列表信息
	 * @return 
	 * */
	private List<Map<String, Object>> getInfoList(List<User> u_list) {
		List<Map<String, Object>> list=new ArrayList<>();
		for(User u:u_list){
			Map<String, Object> map=new HashMap<>();
			map.put("u", u);
			//			System.out.println(mapperUserInfo.selectByPrimaryKey(u.getUserinfoid()));
			map.put("l",transToUserInfoMap(mapperUserInfo.selectByPrimaryKey(u.getUserinfoid())==null?new UserInfo():mapperUserInfo.selectByPrimaryKey(u.getUserinfoid())));
			list.add(map);
		}
		return list;
	}

	/**
	 * 解析userinfo对象的时间格式数据
	 * */
	public Map<String, Object> transToUserInfoMap(UserInfo userInfo){
		Map<String, Object> map=new HashMap<>();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		map.put("id", userInfo.getId()==null? "": userInfo.getId());
		map.put("username", userInfo.getUsername()==null? "": userInfo.getUsername());
		map.put("sex", userInfo.getSex()==null? "": userInfo.getSex());
		map.put("email", userInfo.getEmail()==null? "": userInfo.getEmail());
		map.put("brithday", userInfo.getBrithday()==null? "": df.format(userInfo.getBrithday()));
		map.put("department", userInfo.getDepartment()==null? "": userInfo.getDepartment());
		map.put("hiredate", userInfo.getHiredate()==null? "": df.format(userInfo.getHiredate()));
		map.put("salary", userInfo.getSalary()==null? "": userInfo.getSalary());
		return map;

	}

	private Picture transToPicture(Map<String, String> map) {
		Picture p=new Picture();
		DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
		p.setId(Integer.parseInt(map.get("id")));
		p.setAuthor(map.get("author"));
		p.setAuthorurl(map.get("authorurl"));
		p.setClazz(map.get("clazz"));
		p.setShowurl(map.get("showurl"));
		p.setTheme(map.get("theme"));
		p.setTitle(map.get("title"));
		p.setUrl(map.get("url"));
		try {
			p.setUpdateda(df.parse(map.get("updateDay")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return p;
	}

	/**
	 * 将两个对象的集合合并成Map<String,List<String>>
	 * */
	private Map<String, List<String>> getInfoFromList(List<ClassInfo> list, List<ThemeInfo> list1) {
		Map<String,List<String>> lists=new HashMap<>();
		int temp=0;
		for(int i=0;i<list.size();i++){
			List<String> listo=new ArrayList<>();
			for(int j=temp;j<list1.size();j++){
				if(list.get(i).getClazz().equals(list1.get(j).getClazz())){
					listo.add(list1.get(j).getTheme());
				}else{
					temp=j;
					break;
				}
			}
			lists.put(list.get(i).getClazz(), listo);
		}
		return lists;
	}
	/**
	 * 将两个对象的集合合并成List<Map<String, Object>>
	 * */
	private List<Map<String, Object>> transtoList(List<ClassInfo> list, List<ThemeInfo> list1) {
		List<Map<String,Object>> lists=new ArrayList<>();
		int temp=0;
		for(int i=0;i<list.size();i++){
			Map<String, Object> map=new HashMap<>();
			map.put("title", list.get(i));
			List<ThemeInfo> listo=new ArrayList<>();
			for(int j=temp;j<list1.size();j++){
				if(list.get(i).getClazz().equals(list1.get(j).getClazz())){
					listo.add(list1.get(j));
				}else{
					temp=j;
					break;
				}
			}
			map.put("value", listo);
			lists.add(map);
		}
		return lists;
	}

	private List<Map<String, Object>> transToList(Map<String, OnlineInfo> info){
		Set<String> keys = info.keySet();
		List<Map<String, Object>> list=new ArrayList<>();
		for(String id:keys){
			Map<String, Object> map=new HashMap<>();
			map.put("id", id);
			map.put("obj", info.get(id));
			list.add(map);
		}
		return list;
	}

}
