package com.service.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.dao.entity.PageInfo;
import com.dao.entity.Picture;
import com.dao.entity.ThemeInfo;
import com.dao.impl.PictureMapper;
import com.dao.impl.ThemeInfoMapper;


public class DaoService {
	@Autowired
	private PictureMapper service;
	@Autowired
	private ThemeInfoMapper themeMapper;

	public  PictureMapper getService() {
		return service;
	}
	public  void setService(PictureMapper service) {
		this.service = service;
	}
	
	/**按title查询图片信息
	 * @param info
	 * @return
	 */
	public List<Picture> doSearchByTitle(PageInfo info){
		info.setOffset(info.getOffset()*info.getSize());
		List<Picture> list = service.selectRangToPageByTitle(info);
		//		System.out.println("init query------"+info.getCon());
//		if(list.size()<info.getSize()){
//			info.setCon("%"+info.getCon());
//			info.setSize(info.getOffset()+info.getSize()-list.size());
//			List<Picture> list_o = service.selectRangToPageByTitle(info);
//			for(Picture p:list_o){
//				list.add(p);
//			}
//		}
		return list;
	}
	/**按class查询图片信息
	 * @param info
	 * @return
	 */
	public List<Picture> doSearchByClass(PageInfo info){
		info.setOffset(info.getOffset()*info.getSize());
		List<Picture> list = service.selectRangToPageByClass(info);
		//		System.out.println("init query------"+info.getCon());
//		if(list.size()<info.getSize()){
//			info.setCon("%"+info.getCon());
//			info.setSize(info.getOffset()+info.getSize()-list.size());
//			List<Picture> list_o = service.selectRangToPageByClass(info);
//			for(Picture p:list_o){
//				list.add(p);
//			}
//		}
		return list;
	}
	/**按theme查询图片信息
	 * @param info
	 * @return
	 */
	public List<Picture> doSearchByTheme(PageInfo info){
		info.setOffset(info.getOffset()*info.getSize());
		List<Picture> list = service.selectRangToPageByTheme(info);
		//		System.out.println("init query------"+info.getCon());
//		if(list.size()<info.getSize()){
//			info.setCon("%"+info.getCon());
//			info.setSize(info.getOffset()+info.getSize()-list.size());
//			List<Picture> list_o = service.selectRangToPageByTheme(info);
//			for(Picture p:list_o){
//				list.add(p);
//			}
//		}
		return list;
	}

	/**
	 * 根据theme搜索所有picture
	 * */
	public List<Picture> doSearchByTheme(String theme){
		List<Picture> list = service.selectTheme(theme);
		return list;
	}
	
	/**
	 * 获取10个提示
	 * */
	public List<ThemeInfo> doSearchTheme(String theme){
		List<ThemeInfo> list =themeMapper.selectRangByTheme(theme);
		if(list.size()<11){
			List<ThemeInfo> list_o = themeMapper.selectRangByTheme("%"+theme);
			for(ThemeInfo p:list_o){
				list.add(p);
			}
		}
		return list;
	}

	/**
	 * 对theme的每一个字进行匹配，直至10hint完成
	 * */
//	private List<Picture> getMoreHint(List<Picture> list, String theme, int i){
//		List<Picture> list_o = service.selectRangByTheme("%"+theme.substring(i,i+1));
//		for(Picture p:list_o){
//			list.add(p);
//		}
//		if(list.size()<11&&i<theme.length()-2){
//			list=getMoreHint(list,theme,i);
//		}
//		return list;
//	}

	/**
	 * 搜索图片  theme
	 * */
	public void doSearchPic(List<Picture> list2, HttpServletResponse res){
		
		List<Map<String, String>> list=transToPicMapList(list2);
		if(list.size()>0){
			res.setCharacterEncoding("utf-8");
			try {
				//				System.out.println("query the result size:"+list.size());
				res.getWriter().write(JSON.toJSONString(list));
			} catch (IOException e) {
				System.out.println("error in the get data");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将list集合转化成listmap集合
	 * */
	public List<Map<String, String>> transToPicMapList(List<Picture> list){
		List<Map<String, String>> map=new ArrayList<>();
		DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
		for(Picture p:list){
			Map<String, String> m=new HashMap<>();
			m.put("id", p.getId()+"");
			m.put("author", p.getAuthor());
			m.put("authorurl", p.getAuthorurl());
			m.put("class", p.getClazz());
			m.put("showurl", p.getShowurl());
			m.put("theme", p.getTheme());
			m.put("title", p.getTitle());
			m.put("url", p.getUrl());
			m.put("updateday", df.format(p.getUpdateda()));
			map.add(m);
		}
		return map;

	}

	/**
	 * 搜索提示内容
	 * */
	public void doSearchHint(String con, String method, String area, HttpServletResponse res) {
		List<Map<String, String>> list=this.transToSimTheme(this.doSearchTheme(con+"%"));
		if(list.size()>0){
			res.setCharacterEncoding("utf-8");
			try {
				System.out.println("query the result size:"+list.size());
				res.getWriter().write(JSON.toJSONString(list));
			} catch (IOException e) {
				System.out.println("error in the get data");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 提取图片的theme信息并封装
	 * */
	private List<Map<String, String>>  transToSimTheme(List<ThemeInfo> list){
		List<Map<String, String>> cons=new ArrayList<>();
		for(ThemeInfo p:list){
			Map<String, String>map=new HashMap<>();
			map.put("hint",p.getTheme().length()>7? p.getTheme().substring(0, 6):p.getTheme());
			cons.add(map);
		}
		return cons;
	}
	
	/**
	 * 处理更新列明为theme的数据
	 * @param tar 
	 * */
	public boolean updateTheme(String val, String tar){
		Map<String, String> map=new HashMap<>();
		map.put("tar", tar);
		map.put("val", val);
		if(service.updateTheme(map)>0){
			return true;
		}else{
			return false;
		}
		
	}
	/**
	 * 处理更新列明为clazz的数据
	 * */
	public boolean updateClass(String val, String tar){
		Map<String, String> map=new HashMap<>();
		map.put("tar", tar);
		map.put("val", val);
		if(service.updateClass(map)>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 处理更新一行数据
	 * */
	public boolean updatePic(Picture pic){
		if(service.updateByPrimaryKeySelective(pic)>0){
			return true;
		}else{
			return false;
		}
	}
}
