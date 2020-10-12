package com.scalx.devnews;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DevnewsApplicationTests {

	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(DevnewsApplicationTests.class, args);
	}

	@Test
	void contextLoads() {
	}

}
