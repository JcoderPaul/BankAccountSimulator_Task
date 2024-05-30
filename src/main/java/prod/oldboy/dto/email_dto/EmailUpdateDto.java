package prod.oldboy.dto.email_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import prod.oldboy.validation.annotations.email.EmailUpdateInfo;
import prod.oldboy.validation.group.UpdateAction;

@Value
@FieldNameConstants
@EmailUpdateInfo(groups = {UpdateAction.class})
public class EmailUpdateDto {
    @NotEmpty
    @Email(message = "Wrong format")
    String oldEmail;

    @NotEmpty
    @Email(message = "Wrong format")
    String newEmail;
}
