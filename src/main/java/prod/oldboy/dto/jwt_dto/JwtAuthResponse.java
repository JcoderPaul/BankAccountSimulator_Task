package prod.oldboy.dto.jwt_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {

    private Long id;
    private String login;
    private String accessToken;

}
