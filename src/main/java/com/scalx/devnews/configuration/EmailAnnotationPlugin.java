package com.scalx.devnews.configuration;

import com.scalx.devnews.validation.EmailConstraint;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.bean.validators.plugins.Validators;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import javax.validation.constraints.Max;
import java.util.Optional;

import static springfox.bean.validators.plugins.Validators.annotationFromBean;

// TODO : This is the swagger config of email-annotation
@Component
@Order(Validators.BEAN_VALIDATOR_PLUGIN_ORDER)
public class EmailAnnotationPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    @Override
    public void apply(ModelPropertyContext modelPropertyContext) {

        // Implement Email annotation
//        Optional<String> email = annotationFromBean(modelPropertyContext, String.class);
//
//        if (email.isPresent()) {
//            modelPropertyContext.getBuilder().pattern(".@.\\..*");
//            modelPropertyContext.getBuilder().example("yolo@gmail.com");
//        }
    }
}
