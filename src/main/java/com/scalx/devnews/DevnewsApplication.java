package com.scalx.devnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DevnewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevnewsApplication.class, args);
	}

	// TODO : If @Bean constructor doesn't work, get back to using @PostConstruct
//	@PostConstruct
//	public void initializeCacheLists() {
//		CacheListsConfig.generateLists();
//	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public ActiveUserStore activeUserStore() {
//		return new ActiveUserStore();
//	}
}
