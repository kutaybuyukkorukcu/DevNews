package com.scalx.devnews.configuration;

import com.scalx.devnews.validation.EmailConstraintValidator;
import com.scalx.devnews.validation.PasswordConstraintValidator;
import com.scalx.devnews.validation.PasswordMatchesConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public WebConfig() {
        super();
    }

    @Autowired
    private MessageSource messageSource;

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/", "/resources/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);

        return validator;
    }

    @Bean
    public PasswordMatchesConstraintValidator passwordMatchesConstraintValidator() {
        return new PasswordMatchesConstraintValidator();
    }

    @Bean
    public PasswordConstraintValidator passwordConstraintValidator() {
        return new PasswordConstraintValidator();
    }

    @Bean
    public EmailConstraintValidator emailConstraintValidator() {
        return new EmailConstraintValidator();
    }
}
