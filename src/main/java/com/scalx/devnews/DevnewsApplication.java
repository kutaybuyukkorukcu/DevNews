package com.scalx.devnews;

import com.scalx.devnews.utils.CacheLists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DevnewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevnewsApplication.class, args);
	}

	@PostConstruct
	public void initializeCacheLists() {
		CacheLists.generateLists();
	}
}
