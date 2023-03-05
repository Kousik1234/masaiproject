package com.masaiproject.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneOrEmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneOrEmail {
    String message() default "Invalid phone number or email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
