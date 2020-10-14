package com.scalx.devnews.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class JasyptPropertyService {

    @Value("${spring.datasource.password}")
    private String property;

    public String getProperty() {
        return property;
    }

    public String getPasswordUsingEnvironment(Environment environment) {
        return environment.getProperty("encrypted.property");
    }
}
