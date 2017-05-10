package Tool;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.dao.entity.User;
import com.model.OnlineInfo;

public class SysTool {
	private static Map<String,OnlineInfo> onLineInfo=new TreeMap<>();
	private static int logoUserNum=0;
	private static int registUserNum=0;
	private static boolean canSend=false;
	public static void addSession(String sessionId){
		if(sessionId!=null && sessionId!=""){
			OnlineInfo info=new OnlineInfo();
			info.setId(onLineInfo.size()+"");
			info.setSessionId(sessionId);
			onLineInfo.put(sessionId, info);
			setCanSend(true);
		}
	}
	public static void removeSession(String sessionId) {
		if(sessionId!=null && sessionId!=""){
			onLineInfo.remove(sessionId);
		}
		setCanSend(true);
	}
	public static int getLogoUserNum() {
		return logoUserNum;
	}
	public static void setLogoUserNum(int logoUserNum) {
		SysTool.logoUserNum = logoUserNum;
	}
	public static int getRegistUserNum() {
		return registUserNum;
	}
	public static void setRegistUserNum(int registUserNum) {
		SysTool.registUserNum = registUserNum;
	}
	
	public static void addLogoNum(){
		SysTool.logoUserNum = logoUserNum+1;
		setCanSend(true);
	}
	public static void delLogoNum(){
		if(SysTool.logoUserNum==0){
			SysTool.logoUserNum=0;
		}else{
			SysTool.logoUserNum = logoUserNum-1;
		}
		setCanSend(true);
	}
	public static void addRedistNum(){
		SysTool.registUserNum = registUserNum+1;
	}
	public static void setUserInfo(HttpServletRequest req, User u) {
		String id=req.getSession().getId();
		OnlineInfo info = onLineInfo.get(id);
		if(info==null){
			setSessionInfo(req);
			info = onLineInfo.get(id);
		}
		System.out.println("+++++++++++++");
//		System.out.println("req.getAsyncContext():"+req.getAsyncContext());
//		System.out.println("getLocalAddr():"+req.getLocalAddr());
		System.out.println("getLocalName():"+req.getLocalName());
//		System.out.println("getLocalPort():"+req.getLocalPort());
//		System.out.println("getPathInfo():"+req.getPathInfo());
		System.out.println("getRemoteAddr():"+req.getRemoteAddr());
//		System.out.println("getRequestedSessionId():"+req.getRequestedSessionId());
//		System.out.println("getServerName()"+req.getServerName());
////		System.out.println("getAsyncContext():"+req.getAsyncContext());
//		System.out.println("getServletContext().getServerInfo()"+req.getServletContext().getServerInfo());
//		System.out.println("getQueryString()"+req.getQueryString());
		System.out.println("getRemoteHost()"+req.getRemoteHost());
		System.out.println("++++++++++++++++");
		
		info.setUser(u.getUsername());
		Set<String> keys = onLineInfo.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key=it.next();
			if(onLineInfo.get(key).getUser()==u.getUsername()){
				String s_id=onLineInfo.get(key).getSessionId();
				System.out.println(s_id);
			}
		}
		info.setLevel(u.getLevel());
		onLineInfo.put(req.getSession().getId(), info);
	}
	public static void setSessionInfo(HttpServletRequest req) {
		String sessionId=req.getSession().getId();
		OnlineInfo info = onLineInfo.get(sessionId);
		if(info==null){
			info=new OnlineInfo();
			info.setSessionId(sessionId);
		}
		info.setId(onLineInfo.size()+"");
		info.setHost(req.getServerName());
		info.setState(req.getServerPort()+"");
//		System.out.println(info);
//		System.out.println(req.getLocalAddr());
		onLineInfo.put(sessionId, info);
		setCanSend(true);
		
	}
	public static Map<String, OnlineInfo> getOnLineInfo() {
		return onLineInfo;
	}
	public static void setOnLineInfo(Map<String, OnlineInfo> onLineInfo) {
		SysTool.onLineInfo = onLineInfo;
	}
	
	public static boolean isCanSend() {
		return canSend;
	}
	public static void setCanSend(boolean canSend) {
		SysTool.canSend = canSend;
	}
}
