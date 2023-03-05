package com.masaiproject.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneOrEmailValidator implements ConstraintValidator<PhoneOrEmail, String> {

    private static final String PHONE_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Override
    public void initialize(PhoneOrEmail constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

        Matcher phoneMatcher = phonePattern.matcher(value);
        Matcher emailMatcher = emailPattern.matcher(value);

        return phoneMatcher.matches() || emailMatcher.matches();
    }
}

