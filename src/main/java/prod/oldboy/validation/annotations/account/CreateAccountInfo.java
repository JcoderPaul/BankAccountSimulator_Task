package prod.oldboy.validation.annotations.account;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prod.oldboy.validation.impl.account_validators.CreateAccountValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CreateAccountValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateAccountInfo {

    String message() default "One ore more fields is empty / " +
                             "(Login, Phone, Email) is exist already";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
