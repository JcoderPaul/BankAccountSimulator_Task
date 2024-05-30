package prod.oldboy.validation.impl.email_validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import prod.oldboy.dto.email_dto.EmailUpdateDto;
import prod.oldboy.repository.email_repo.EmailRepository;
import prod.oldboy.validation.annotations.email.EmailUpdateInfo;

@Component
@RequiredArgsConstructor
public class EmailUpdateValidator implements ConstraintValidator<EmailUpdateInfo, EmailUpdateDto> {

    private final EmailRepository emailRepository;

    @Override
    public boolean isValid(EmailUpdateDto emailUpdateDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return (StringUtils.hasText(emailUpdateDto.getOldEmail()) &&
                StringUtils.hasText(emailUpdateDto.getNewEmail()) &&
                emailRepository.findEmail(emailUpdateDto.getOldEmail()).isPresent() &&
                emailRepository.findEmail(emailUpdateDto.getNewEmail()).isEmpty() &&
                emailUpdateDto.getNewEmail().contains("@"));
    }
}