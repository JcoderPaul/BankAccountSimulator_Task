package prod.oldboy.dto.phone_dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import prod.oldboy.validation.annotations.phone.PhoneUpdateInfo;
import prod.oldboy.validation.group.UpdateAction;

@Value
@FieldNameConstants
@PhoneUpdateInfo(groups = {UpdateAction.class})
public class PhoneUpdateDto {

    @NotEmpty
    @Size(min = 6, max = 12)
    @Pattern(regexp = "[0-9]+", message = "Wrong format or short/long")
    String oldPhone;

    @NotEmpty
    @Size(min = 6, max = 12)
    @Pattern(regexp = "[0-9]+", message = "Wrong format or short/long")
    String newPhone;

}
