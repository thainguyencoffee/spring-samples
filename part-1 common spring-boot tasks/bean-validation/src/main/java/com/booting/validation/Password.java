package com.booting.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordRuleValidator.class)
public @interface Password {
    String message() default "Mật khẩu không tuân theo quy tắc đã chỉ định";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
