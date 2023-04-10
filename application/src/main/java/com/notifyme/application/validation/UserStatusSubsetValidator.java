package com.notifyme.application.validation;

import javax.validation.ConstraintValidator;

import javax.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UserStatusSubsetValidator implements ConstraintValidator<UserStatusSubset, UserStatus> {

    private UserStatus[] subset;

    @Override
    public void initialize(UserStatusSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(UserStatus value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }

}
