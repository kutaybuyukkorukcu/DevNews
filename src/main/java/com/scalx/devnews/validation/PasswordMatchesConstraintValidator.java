package com.scalx.devnews.validation;

import com.scalx.devnews.entity.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class PasswordMatchesConstraintValidator implements ConstraintValidator<PasswordMatchesConstraint, Object> {

    @Override
    public void initialize(PasswordMatchesConstraint passwordMatchesConstraint) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        // TODO : Change User to UserDto/UserRequest-Response after implementing
        User user = (User) o;

//        return user.getPassword().equals(user.getMatchingPassword());
        return false;
    }
}
