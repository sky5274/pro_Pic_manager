package test;

import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.entity.PageInfo;
import com.dao.entity.Picture;
import com.dao.entity.TypeInfo;
import com.dao.entity.User;
import com.dao.entity.UserInfo;
import com.dao.impl.PictureMapper;
import com.dao.impl.UserInfoMapper;
import com.dao.impl.UserMapper;

import Tool.ServiceTool;

public class test1 {
	
	@Test
	public void testSelect1(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		PictureMapper mapper=con.getBean(PictureMapper.class);
//		java.util.List<Picture> list = mapper.selectRangByTitle("%土里%");
		PageInfo info=new PageInfo("%人%",1,10);
		TypeInfo inf=new TypeInfo("title", "%土里%");
		java.util.List<Picture> list = mapper.selectRangToPageByTitle(info);
//		java.util.List<Picture> list = mapper.selectRangToPageByClass(info);
		
		for(Picture p:list){
			System.out.println(p);
		}
		
	}
	@Test
	public void testSelect2(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		PictureMapper mapper=con.getBean(PictureMapper.class);
		System.out.println(mapper.getClass().getName());
		PageInfo info=new PageInfo("%土%",1,10);
		java.util.List<Picture> list = mapper.selectRangToPageByTitle(info);
		for(Picture p:list){
			System.out.println(p);
		}
		
		System.out.println(mapper.selectByPrimaryKey(2));
	}
	
	@Test
	public void testSelect3(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		PictureMapper mapper=con.getBean(PictureMapper.class);
		PageInfo info=new PageInfo("%海洋%",1,10);
		java.util.List<Picture> list = mapper.selectRangToPageByTheme(info);
		for(Picture p:list){
			System.out.println(p);
		}
		
	}
	
	@Test
	public void testSelect4(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		PictureMapper mapper=con.getBean(PictureMapper.class);
		List <Picture> list=mapper.selectRangByTheme("%海%");
		for(Picture p:list){
			System.out.println(p);
		}
		
	}
	
	@Test
	public void testuserMapper1(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		UserMapper mapper=con.getBean(UserMapper.class);
		System.out.println(mapper.selectALL().size());
		User list=mapper.selectByPrimaryKey(3);
			System.out.println(list);
	}
	
	@Test
	public void testuserMapper2(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		UserMapper mapper=con.getBean(UserMapper.class);
		User u=new User();
		u.setUsername("123");
		u.setPassword("coV3m5qa");
		User list=mapper.selectByUser(u);
		System.out.println(list);
	}
	
	@Test
	public void testuserMapper3(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		UserMapper mapper=con.getBean(UserMapper.class);
		User u=new User();
		u.setUsername("1sdf");
		u.setPassword("544");
		u.setLevel(1);
		u.setUserinfoid(1);
		u=ServiceTool.addEcodeUser(u);
		mapper.insert(u);
//		User list=mapper.selectByUser(u);
//			System.out.println(list);
	}
	
	@Test
	public void testuserMapper4(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		UserMapper mapper=con.getBean(UserMapper.class);
		User u=new User();
		u.setUsername("%%");
		List<User> us = mapper.selectVisitorRangeByName(u.getUsername());
		for(User s:us){
			System.out.println(s);
			System.out.println(con.getBean(UserInfoMapper.class).selectByPrimaryKey(s.getUserinfoid()));
		}
	}
	
	@Test
	public void testuseInforMapper1(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		UserInfoMapper mapper=con.getBean(UserInfoMapper.class);
		UserInfo u=new UserInfo();
		u.setUsername("tom");
		User ul=new User();
		ul.setUserinfoid(1);
		ul.setUsername("tom");
//		u.setSex("nv");
//		u.setEmail("asdfj@163.com");
//		u.setBrithday(new Date());
//		User list=mapper.selectByUser(u);
//			System.out.println(list);
	}
	
	@Test
	public void testuseInforMapper2(){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		UserInfoMapper mapper=con.getBean(UserInfoMapper.class);
		UserInfo u=new UserInfo();
//		u.setId(2);
		u.setUsername("tim");
		u.setSex("nv");
		u.setEmail("asdsdfj@163.com");
		u.setBrithday(new Date());
		u.setHiredate(new Date());
		u.setDepartment("office");
		mapper.insertSelective(u);
//		System.out.println(mapper.selectByUserInfo(u));
//		User list=mapper.selectByUser(u);
//			System.out.println(list);
	}
	public static void main(String [] args){
		ApplicationContext con=new ClassPathXmlApplicationContext("config/ApplicaiotnContext.xml");
		UserInfoMapper mapper=con.getBean(UserInfoMapper.class);
		UserInfo u=new UserInfo();
//		u.setId(2);
		u.setUsername("tom");
		u.setSex("nv");
		u.setDepartment("sal");
		u.setEmail("asdfj@163.com");
		u.setSalary(12.0);
		u.setBrithday(new Date());
		u.setHiredate(new Date());
		mapper.insert(u);
	}
}
