package prod.oldboy.validation.annotations.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prod.oldboy.validation.impl.email_validators.EmailCreateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailCreateValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailCreateInfo {

    String message() default "Email is already exist /" +
                             "Field is empty / " +
                             "Wrong format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
