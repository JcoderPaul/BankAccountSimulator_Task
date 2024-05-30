package prod.oldboy.dto.phone_dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import prod.oldboy.validation.annotations.phone.PhoneCreateInfo;
import prod.oldboy.validation.group.CreateAction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PhoneCreateInfo(groups = {CreateAction.class})
public class PhoneCreateDto {

    @NotEmpty
    @Size(min = 6, max = 12)
    @Pattern(regexp = "[0-9]+", message = "Wrong format or short/long")
    String createPhone;

}
