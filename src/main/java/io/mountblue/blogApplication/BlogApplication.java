package io.mountblue.blogApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);

//		String uName = SecurityContextHolder.getContext().getAuthentication().getName();
//		System.out.println(uName);
	}

}
