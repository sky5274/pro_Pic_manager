package com.crawl;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan("com.ceawl.data.*.dao")
public class WebCwaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCwaleApplication.class, args);
	}

}

