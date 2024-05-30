package prod.oldboy.validation.annotations.account;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prod.oldboy.validation.impl.account_validators.JwtAuthRequestValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = JwtAuthRequestValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtAuthRequestInfo {

    String message() default "Empty fields / " +
                             "Login not exist / " +
                             "Password not correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
