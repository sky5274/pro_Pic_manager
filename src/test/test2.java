package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.entity.ClassInfo;
import com.dao.entity.ThemeInfo;
import com.dao.impl.ClassInfoMapper;
import com.dao.impl.PictureMapper;
import com.dao.impl.ThemeInfoMapper;

public class test2 {
	
	@Test
	public void testAdminquery1(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		ClassInfoMapper mapper=con.getBean(ClassInfoMapper.class);
		List<ClassInfo> list = mapper.selectInfo();
		for(ClassInfo c:list){
			System.out.println(c);
		}
	}
	
	@Test
	public void testAdminquery2(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		ThemeInfoMapper mapper=con.getBean(ThemeInfoMapper.class);
		List<ThemeInfo> list = mapper.selectInfo();
		for(ThemeInfo c:list){
			System.out.println(c);
		}
	}
	
	@Test
	public void testAdminquery3(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		ThemeInfoMapper mapper1=con.getBean(ThemeInfoMapper.class);
		List<ThemeInfo> list1 = mapper1.selectInfo();
		ClassInfoMapper mapper=con.getBean(ClassInfoMapper.class);
		List<ClassInfo> list = mapper.selectInfo();
		List<Map<String, Object>> the = transtoList(list, list1);
		for(Map<String, Object> m:the){
			System.out.println("---------------------");
			System.out.print("\t"+"title:"+m.get("title")+"\n");
			System.out.print("\t"+"value:\n");
			List<ThemeInfo> li=(List<ThemeInfo>) m.get("value");
			for(ThemeInfo t:li){
				System.out.print("\t\t"+t+"\n");
			}
			
		}
	}
	
	@Test
	public void testAdminquery4(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		ThemeInfoMapper mapper1=con.getBean(ThemeInfoMapper.class);
		List<ThemeInfo> list1 = mapper1.selectInfo();
		ClassInfoMapper mapper=con.getBean(ClassInfoMapper.class);
		List<ClassInfo> list = mapper.selectInfo();
		Map<String, List<String>> the = getInfoFromList(list, list1);
		Set<String> keys = the.keySet();
		for(String s:keys){
			System.out.println("---------------------");
			System.out.print("\t"+"class:"+s+"\n");
			System.out.print("\t"+"value:\n");
			List<String> li= the.get(s);
			for(String t:li){
				System.out.print("\t\t"+t+"\n");
			}
			
		}
	}
	@Test
	public void testAdminupdate1(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		PictureMapper mapper1=con.getBean(PictureMapper.class);
		Map<String, String> map=new HashMap<>();
		map.put("tar", "山水图片1");
		map.put("val", "山水图片");
		
		int i = mapper1.updateTheme(map);
		System.out.println(i);
	}
	
	@Test
	public void testAdminupdate2(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		PictureMapper mapper1=con.getBean(PictureMapper.class);
		Map<String, String> map=new HashMap<>();
		map.put("tar", "动物图");
		map.put("val", "动物图片");
		
		int i = mapper1.updateClass(map);
		System.out.println(i);
	}
	
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

}
