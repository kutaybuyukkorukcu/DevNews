package com.scalx.devnews;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@TestPropertySource(locations = "C:\\Users\\Kutay\\IdeaProjects\\devnews\\src\\test\\resources\\application.properties")
@EnableTransactionManagement
public class H2JpaConfig {
}
