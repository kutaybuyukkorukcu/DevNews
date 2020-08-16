package com.scalx.devnews.validation;

import com.scalx.devnews.entity.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> klass) {
        // TODO : Change User to UserDto/UserRequest-Response after implementing
        return User.class.isAssignableFrom(klass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Username is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Password is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email is required");
    }
}
