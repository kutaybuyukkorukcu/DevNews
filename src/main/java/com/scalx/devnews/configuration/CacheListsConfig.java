package com.scalx.devnews.configuration;

import com.scalx.devnews.utils.CacheLists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheListsConfig {

    @Bean
    public CacheLists cacheLists() {
        return new CacheLists();
    }
}
