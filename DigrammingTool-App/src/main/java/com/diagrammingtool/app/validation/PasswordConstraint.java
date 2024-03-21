package com.diagrammingtool.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Password must contain at least 8 characters including one uppercase letter one lowercase letter one number and one special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {}; 
}
