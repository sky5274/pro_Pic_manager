package com.web.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dao.entity.PageInfo;
import com.dao.entity.Picture;
import com.service.dao.DaoService;

import Tool.SysTool;


@Controller
public class CheckInfoControl {
	@Autowired
	private DaoService daoservice;

	/**
	 * 获取session属性，<br>
	 * */
	@RequestMapping("/GetSession")
	public void getSessionAttr(HttpServletRequest req,HttpServletResponse res){
		String data=req.getParameter("data");
		System.out.println("session req:"+data);
		String name=(String) req.getSession().getAttribute(data);
		SysTool.setSessionInfo(req);
		try {
			if(name!=null){
				res.getWriter().write(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置属性，<br>
	 * 如果请求内容是：rm_session_user ； 删除session中的username属性
	 * */
	@RequestMapping("/setAttribute")
	public void setAttribute(HttpServletRequest req,HttpServletResponse res){
		String data=req.getParameter("request");
		if(data!=null&& data!=""){
			switch (data) {
			case "rm_session_user":
				req.getSession().removeAttribute("username");
				try {
					res.getWriter().println("suc_rm_session_user");
					SysTool.delLogoNum();   //减少在线人数
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}
	}
	
	/**
	 * 搜索框中，智能提示，（提示内容：Theme名）
	 * */
	@RequestMapping("/searchInfo")
	public void getSearchHint(HttpServletRequest req,HttpServletResponse res){
		String con=req.getParameter("context");
		String method=req.getParameter("method");
		String area=req.getParameter("areas").trim();
		//		System.out.println(method+":"+area);
		if(area!=""){
			daoservice.doSearchHint(con, method, area, res);
		}
		//		temp=area;
	}
	/**
	 * 通过ajax在当前页面添加图片
	 * @param req
	 * @param res
	 */
	@RequestMapping("/getNextContext")
	public void getSearchContext(HttpServletRequest req,HttpServletResponse res){
		String con=req.getParameter("data");
		int i=new Integer(req.getParameter("offset"));
//		System.out.println(con+":"+i);
		String[] cons=con.split("-");
		PageInfo info=new PageInfo(cons[2], i, 10);
		List<Picture> list=null;
		switch (cons[1]) {
		case "t":
//			System.out.println("1");
			 list = daoservice.doSearchByTitle(info);
			break;
		case "m":
			 list = daoservice.doSearchByClass(info);
			break;
		case "th":
			 list = daoservice.doSearchByTheme(info);
			break;
		default:
			break;
		}
		daoservice.doSearchPic(list, res);
		
	}
	/**提交搜索请求
	 * 
	 * @param search
	 * @param req
	 * @return
	 */
	@RequestMapping("/commitSearch")
	public ModelAndView getCommitContext(String search, HttpServletRequest req){
		ModelAndView view =new ModelAndView("picshow");

		PageInfo info=new PageInfo(search, 0, 20);
		String type="th";    //search   by theme
		List<Map<String, String>> list = daoservice.transToPicMapList(daoservice.doSearchByTheme(info));
		view.addObject("pic", list);
		view.addObject("title", search);
		view.addObject("pic", list);
		view.addObject("list", getPageLead(info,req,type));
		return view;
	}

	/**通过分页按钮导航
	 * 
	 * @param type
	 * @param context
	 * @param curpage
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/img-{type}-{context}-{curpage}",method=RequestMethod.GET)
	public ModelAndView getSearchPic(@PathVariable String type,@PathVariable String context,@PathVariable String curpage,HttpServletRequest req){
		ModelAndView view =new ModelAndView("picshow");
		PageInfo info=new PageInfo(context, new Integer(curpage), 20);
		List<Map<String, String>> list=new ArrayList<>();
		switch (type) {
		case "t":   //search   by title
			list = daoservice.transToPicMapList(daoservice.doSearchByTitle(info));
			break;
		case "m":  //search   by class
			list = daoservice.transToPicMapList(daoservice.doSearchByClass(info));
			break;
		case "th":  //search   by theme
			list = daoservice.transToPicMapList(daoservice.doSearchByTheme(info));
			break;

		default:
			break;
		}
		view.addObject("pic", list);
		view.addObject("title", context);
		view.addObject("list", getPageLead(info,req,type));
		return view;		
	}
	
	/**转化分页导航
	 * @param info
	 * @param req
	 * @param type
	 * @return
	 */
	private List<Map<String, String>> getPageLead(PageInfo info, HttpServletRequest req, String type){
		String context=info.getCon();
		int last = daoservice.doSearchTheme("%"+context+"%").size()%20==0?daoservice.doSearchTheme("%"+context+"%").size()%20:daoservice.doSearchTheme("%"+context+"%").size()%20+1;
		List< Map<String, String>> conlist=new ArrayList<>();
		Map<String, String> map=new HashMap<>();
		map.put("首页", "href='"+req.getContextPath()+"/img-"+type+"-"+info.getCon()+"-"+0+".do'");
		conlist.add(map);
		Map<String, String> map0=new HashMap<>();
		map0.put("<<", "href='"+req.getContextPath()+"/img-"+type+"-"+info.getCon()+"-"+(info.getOffset()-1)+".do'");
		conlist.add(map0);
		int start=0,end=0;
		if(info.getOffset()<7){
			start=0;
			
		}else{
			start=info.getOffset()-7;
		}
		if(last<13){
			end=last;
		}else{
			if(info.getOffset()+7<last){
				if(info.getOffset()+7>13){
					end=info.getOffset()+7;
				}else{
					end=13;
				}
			}else{
				end=last;
			}
		}
		for(int i=start;i<end;i++){
			Map<String, String> map_x=new HashMap<>();
			if(i==info.getOffset()){
				map_x.put(i+"", "");
			}else{
				map_x.put(i+"", "href='"+req.getContextPath()+"/img-"+type+"-"+info.getCon()+"-"+i+".do'");
			}
			conlist.add(map_x);
		}
		Map<String, String> map1=new HashMap<>();
		map1.put(">>","href='"+req.getContextPath()+"/img-"+type+"-"+info.getCon()+"-"+(info.getOffset()-1)+".do'");
		conlist.add(map1);
		Map<String, String> map2=new HashMap<>();
		map2.put("尾页", "href='"+req.getContextPath()+"/img-"+type+"-"+info.getCon()+"-"+last+".do'");
		conlist.add(map2);
		return conlist;
	}
}
