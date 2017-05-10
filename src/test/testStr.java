package test;

import org.junit.Test;

public class testStr {
	@Test
	 public void test1(){
		 String message="{\"requst\":\"get_server_info\"}";
		 message=message.substring(1,message.length()-1);
		 String[] ss=message.split(":");
		 for(String s:ss){
			 System.out.println(s.replaceAll("\"", ""));
		 }
	 }
}
