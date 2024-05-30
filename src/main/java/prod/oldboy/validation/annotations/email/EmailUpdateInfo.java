package prod.oldboy.validation.annotations.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prod.oldboy.validation.impl.email_validators.EmailUpdateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailUpdateValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailUpdateInfo {

    String message() default "Old email not find / " +
                             "New email is already exist / " +
                             "Fields is empty /" +
                             "Wrong format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}