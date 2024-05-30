package prod.oldboy.dto.jwt_dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import prod.oldboy.validation.annotations.account.JwtAuthRequestInfo;
import prod.oldboy.validation.group.LogInAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JwtAuthRequestInfo(groups = {LogInAction.class})
public class JwtAuthRequest {

    @NotEmpty
    @Size(min = 3, max = 64, message = "Wrong format (to short/to long)")
    private String login;

    @NotEmpty
    @Size(min = 3, max = 128, message = "Wrong format (to short/to long)")
    private String password;
}
