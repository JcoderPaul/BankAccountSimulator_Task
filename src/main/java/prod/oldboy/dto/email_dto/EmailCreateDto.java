package prod.oldboy.dto.email_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import prod.oldboy.validation.annotations.email.EmailCreateInfo;
import prod.oldboy.validation.group.CreateAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EmailCreateInfo(groups = {CreateAction.class})
public class EmailCreateDto {

    @NotEmpty
    @Email(message = "Wrong format")
    String createEmail;

}
