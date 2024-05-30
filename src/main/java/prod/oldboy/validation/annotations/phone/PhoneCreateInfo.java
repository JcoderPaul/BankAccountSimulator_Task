package prod.oldboy.validation.annotations.phone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prod.oldboy.validation.impl.phone_validators.PhoneCreateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneCreateValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneCreateInfo {

    String message() default "This phone is already exist /" +
                             "Field is empty /" +
                             "Wrong format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
