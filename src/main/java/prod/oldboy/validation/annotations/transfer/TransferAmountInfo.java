package prod.oldboy.validation.annotations.transfer;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prod.oldboy.validation.impl.amount_validators.AmountTransferValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AmountTransferValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransferAmountInfo {

    String message() default "Empty fields /" +
                             "Account ID not exist /" +
                             "The bank does not work with tugriks!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
