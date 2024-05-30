package prod.oldboy.validation.annotations.phone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prod.oldboy.validation.impl.phone_validators.PhoneUpdateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneUpdateValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneUpdateInfo {

    String message() default "Old phone not find / " +
                             "New phone is already exist / " +
                             "Fields is empty/" +
                             "Enter phones number to short/to long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
