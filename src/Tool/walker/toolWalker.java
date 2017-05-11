package Tool.walker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.entity.Picture;



public class toolWalker {

	/**
	 * 根据给定的url路径读取页面的源代码
	 * */
	@SuppressWarnings("finally")
	public static  String readAddr(String url){
		StringBuffer context=new StringBuffer();
		try {
			URL u=new URL(url);
			BufferedReader read=new BufferedReader(new InputStreamReader(u.openStream()));
			String line;
			while((line=read.readLine())!=null){
				context.append(line);
			}
			read.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error:io is bad");
			e.printStackTrace();
		}finally{
			return context.toString();
		}
		
	}

	/**
	 * @param context    页面的源代码
	 * @param  tar      所要提取信息的所在元素标签的关键字，如：class，id，元素名
	 * @return picture集合
	 * */
	public static List<Picture> walkerPicture(String context,String tar){
		List<node> n=analysByClass(context, tar,0);
		List<Picture> pic_list=new ArrayList<>();
		for(int i=0;i<n.size();i++){
			if(!n.get(i).getContext().contains("</a>")){
				continue;
			}
//			System.out.println(n.get(i).getContext());
			pic_list.add(transToPicture(n.get(i).getContext()));
		}
		return pic_list;
	}
	
	/**
	 * @param node 节点
	 * @return  map集合
	 * */
	public static Map<String, Map<String, String>> getMenu(node node){
		List<node> list=SelectNodeInfo.findByElement(node, "li");
		Map<String, Map<String, String>> map=new HashMap<>();
		for(node n:list){
			List<node> nodes=SelectNodeInfo.findByElement(n, "a");
			Map<String, String> menu=new HashMap<>();
			String menuname="";
			for(int i=0;i<nodes.size();i++){
				String context=nodes.get(i).getContext();
				int s=context.indexOf("href=")+5;
				int e=context.indexOf(">");
				String url=context.substring(s,e).split("\"")[1];
				String item=nodes.get(i).getInnerHTML();
				if(item.contains("<strong>")){
					menuname=item.split("<strong>")[1].split("</strong>")[0];
					item=menuname;
				}
				//System.out.println(item+":     "+url);
				menu.put(item, url);
			}
			map.put(menuname, menu);
		}
		return map;
	}
	
	/**
	 * @param String  截取的字节
	 * @return  封装成picture对象
	 * */
	private static Picture transToPicture(String context){
		int href_end=context.indexOf("target=\"_blank\"");
		int hrer_s=context.indexOf("href=\"http://www.tooopen.com");
		String showurl=context.substring(hrer_s, href_end).split("\"")[1];
		int title_s=context.indexOf("title=",href_end);
		int title_e=context.indexOf(">",href_end);
		String title=context.substring(title_s, title_e).split("\"")[1];
		int url_s=context.indexOf("img src=",href_end);
		int url_e=context.indexOf("onerror=",href_end);
		String pic_url=context.substring(url_s, url_e).split("\"")[1];
		int authorurl_s=context.indexOf("<a href=",href_end);
		int authorurl_e=context.indexOf("title=",authorurl_s);
		String authorurl=context.substring(authorurl_s, authorurl_e).split("\"")[1];
		int author_s=authorurl_e+6;
		int autho_e=context.indexOf(">",author_s);
		String author=context.substring(author_s,autho_e).split("\"")[1];
		int update_s=context.indexOf("上传于")+3;
		int update_e=context.indexOf("</em>",update_s);
		String update=context.substring(update_s, update_e);
//		System.out.println(showurl+"\t"+title+"\t"+authorurl+"\t"+pic_url+"\t"+author+"\t"+update);
		Picture p=new Picture();
		p.setShowurl(showurl);
		p.setTitle(title);
		p.setAuthorurl(authorurl);
		p.setUrl(pic_url);
		p.setAuthor(author);
		DateFormat df=new SimpleDateFormat("yyyy-mm-dd");
		Date d=null;
		try {
			d=df.parse(update);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.setUpdateda(d);
		
		return p;
	}

	/**
	 * 根据标签获取节点元素
	 * @param ele_s 
	 * */
	public static List<node> analysByElement(String context, String ele, int ele_s){
		List<node> list=new ArrayList<node>();
		node n;
		while((n=analysElement(context, ele, ele_s))!=null){
			list.add(n);
			ele_s+=n.getContext().length();
		}
		return list;
	}
	/**
	 * 分析字符串，以元素名将其转化成node节点
	 * @param ele  :节点元素名
	 * @param ele_s  ：节点起始位置
	 * @param context  ：字符串
	 * @return node
	 * */
	private static node analysElement(String context, String ele, int ele_s){
		int start=context.indexOf(ele,ele_s);
		int end=getEndIndex(context, ele, start, ele_s);
		return transToNodes(context.substring(start-1,end+2));
	}
	/**
	 * 分析字符串，以元素的class将其转化成node节点
	 * @param classes  :节点元素的class属性
	 * @param ele_start  ：节点起始位置
	 * @param context  ：字符串
	 * @return node
	 * */
	private static node analysClass(String context, String classes,int ele_start){
		int start=context.indexOf(classes,ele_start);
		if(start>0){
			for(int i=0;i<100;i++){
				int ele_s=start-i;
				if(context.charAt(ele_s)=='<'){
					int ele_e=context.indexOf(" ",ele_s);
					return analysElement(context, context.substring(ele_s+1, ele_e).trim(),ele_s);
				}
			}
		}
		return null;
	}
	/**
	 * 分析字符串，以元素的class将其转化成node节点
	 * @param classes  :节点元素的class属性
	 * @param ele_start  ：节点起始位置
	 * @param context  ：字符串
	 * @return node
	 * */
	public static List<node> analysByClass(String context, String classes,int ele_start){
		List<node> list=new ArrayList<node>();
		node n;
		while((n=analysClass(context, classes, ele_start))!=null){
			if(ele_start<0){
				break;
			}
			list.add(n);	
			ele_start=context.indexOf(classes,ele_start+1);
		}
		return list;
	}

	/**
	 * 转换成节点
	 * */
	public static node transToNodes(String con) {
		node n=new node();
		int ele_s=con.lastIndexOf("</");
		int ele_e=con.indexOf(">",ele_s);
		if(ele_s>=0&&ele_e>ele_s){
			String ele=con.substring(ele_s+2,ele_e);
			n.setElement(ele);
			//int ele_i=con.indexOf("ele");
			int head_s=con.indexOf("<");
			int head_e=con.indexOf(">");
			List<String> list=transAttrToLIst(con.substring(head_s, head_e+1).split("="));
			Map<String, String[]> attr=new HashMap<>();
			for(int i=0;i<list.size();i+=2){
				if(list.get(i)=="class"){
					attr.put("class", list.get(i+1).split(" "));
					n.setClasses(list.get(i+1).split(" "));
				}else if(list.get(i)=="id"){
					attr.put("id", list.get(i+1).split(" "));
					n.setId(list.get(i+1));
				}else{
					attr.put(list.get(i), list.get(i+1).split(" "));
					
				}

			}
			n.setAttr(attr);
//			System.out.println("inner:"+con.substring(head_e+1, ele_s));
			if(head_s>=0 &&ele_s>=0){
				n.setInnerHTML(con.substring(head_e+1, ele_s).trim());
				n.setChildNode(getChildNode(con.substring(head_e+1, ele_s).trim()));   //设置子节点
			}
			n.setContext(con);
			return n;
		}else{
			return null;
		}


	}

	/**
	 * 获取子节点
	 * */
	private static Map<String, node> getChildNode(String innerHtml){
		int start=0;
		int ele_s;
		Map<String, node> ChildMap=new HashMap<>();
		int i=0;
		/*
		 * 寻找子节点
		 * */
		while((ele_s=innerHtml.indexOf("<",start))>=0){
			if(innerHtml.charAt(ele_s+1)=='/'){
				start=ele_s+1;
				continue;
			}
			int ele_e=innerHtml.indexOf(" ",ele_s)>innerHtml.indexOf(">",ele_s)?innerHtml.indexOf(">",ele_s):innerHtml.indexOf(" ",ele_s);

			if(ele_e>0){
				int ele_end;
				String ele=innerHtml.substring(ele_s+1, ele_e).trim();
				if(!ele.contains("--")){
//					System.out.println(innerHtml.substring(ele_s+1, ele_e));
					ele_end=getEndIndex(innerHtml, ele, ele_s, ele_e);
//					System.out.println("text:   "+ele_s+":"+ele_end);
//					System.out.println("childinner: "+transToNodes( innerHtml.substring(ele_s, ele_end)).getInnerHTML());
					ChildMap.put(ele+"-"+i, transToNodes( innerHtml.substring(ele_s, ele_end)));
					i++;
				}else{
					ele_end=getEndIndex(innerHtml, "--", ele_s+2, ele_e);
				}
				start=ele_end+1;
			}else{
				break;
			}
		}
		return ChildMap;
	}

	/**
	 * 寻找对应节点的结尾
	 * */
	private static int getEndIndex(String context, String ele,int start,int ends){
		int end ;
		if(ele=="--"){
			end=context.indexOf(ele+">",ends)+3;
//			System.out.println(end+":"+start+":"+ends);
		}else{
			end=context.indexOf("</"+ele+">",ends)>0?context.indexOf("</"+ele+">",ends)+ele.length()+3:context.indexOf("/>",ends)+3;
		}
		int s=context.indexOf("<"+ele,ends+1);

//		System.out.println("---------------------------------------------");
//		System.out.println("test:"+end+":"+context.indexOf(">",end-1));
				if(s>start&&s<end){
					int temp=getEndIndex(context, ele, start, end);
					end=temp>end?temp:end;
				}

//		System.out.println(end+":"+start+":"+s+":"+ends);
//
//		System.out.println("\t元素："+ele);
		//		System.out.println(context);
//		System.out.println(context.substring(start, end));
		return end;

	}

	/**
	 * 将元素的属性转化为集合
	 * */
	private static List<String> transAttrToLIst(String[] cs){
		List<String> list=new ArrayList<String>();
		if(cs.length>2){
			for(int i=0;i<cs.length;i++){
				if(i==0){
					list.add(cs[i].split(" ")[1]);
				}else if(i==cs.length-1){
					list.add(cs[i].split("\"")[0]);
				}else{
					list.add(cs[i].split("\"")[0]);
					list.add(cs[i].split("\"")[1].trim());
				}
			}
		}
		return list;
	}
	/**
	 * 获取网页菜单menu的request访问路径的规则
	 * */
	public static Map<String, String> getRule(String context,String tar,String action){
		List<node> n=analysByClass(context, tar,0);
		String con=null;
		try {
			con=n.get(0).getContext();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		int s=con.indexOf("<a href=");
		int e=con.indexOf(">",s);
		String rule=con.substring(s,e).split("\"")[1];
		String rule_h=rule.split(".aspx")[0];
//		System.out.println(rule);
		rule_h=rule_h.substring(0, rule_h.length()-1);
		Map< String, String> rules=new HashMap<>();
		rules.put("header", rule_h);
		rules.put("action", action);
		return rules;
	}

}
