package prod.oldboy.dto.account_dto;

import jakarta.validation.constraints.*;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import prod.oldboy.validation.annotations.account.CreateAccountInfo;
import prod.oldboy.validation.group.CreateAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@FieldNameConstants
@CreateAccountInfo(groups = {CreateAction.class})
public class AccountCreateDto {

    @NotEmpty
    @Size(min = 3, max = 64, message = "Wrong format (to short/to long)")
    String login;

    @NotEmpty
    @Size(min = 3, max = 128, message = "Wrong format (to short/to long)")
    String pass;

    @NotEmpty
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[A-Za-z]+|[А-Яа-я]+", message = "Wrong format - only letters")
    String lastname;

    @NotEmpty
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[A-Za-z]+|[А-Яа-я]+", message = "Wrong format - only letters")
    String firstname;

    @NotEmpty
    @Size(min = 1, max = 64)
    @Pattern(regexp = "[A-Za-z]+|[А-Яа-я]+", message = "Wrong format - only letters")
    String patronymic;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    LocalDate birthDate;

    @NotEmpty
    @Email(message = "Wrong format")
    String email;

    @NotEmpty
    @Size(min = 6, max = 12)
    @Pattern(regexp = "[0-9]+", message = "Wrong format or short/long")
    String phone;
    
    @NotNull
    @Min(value = 0)
    @Digits(integer = 14, fraction = 2)
    BigDecimal amount;
}
