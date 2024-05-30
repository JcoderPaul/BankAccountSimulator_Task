package prod.oldboy.validation.impl.account_validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import prod.oldboy.dto.jwt_dto.JwtAuthRequest;
import prod.oldboy.repository.account_repo.AccountRepository;
import prod.oldboy.validation.annotations.account.JwtAuthRequestInfo;

@Component
@RequiredArgsConstructor
public class JwtAuthRequestValidator implements ConstraintValidator<JwtAuthRequestInfo, JwtAuthRequest> {

    private final AccountRepository accountRepository;

    @Override
    public boolean isValid(JwtAuthRequest jwtAuthRequest,
                           ConstraintValidatorContext constraintValidatorContext) {

        return (StringUtils.hasText(jwtAuthRequest.getLogin()) &&
                StringUtils.hasText(jwtAuthRequest.getPassword()) &&
                accountRepository.findByLogin(jwtAuthRequest.getLogin()).isPresent());
    }
}
