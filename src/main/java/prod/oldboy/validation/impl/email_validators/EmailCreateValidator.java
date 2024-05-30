package prod.oldboy.validation.impl.email_validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import prod.oldboy.dto.email_dto.EmailCreateDto;
import prod.oldboy.repository.email_repo.EmailRepository;
import prod.oldboy.validation.annotations.email.EmailCreateInfo;
@Component
@RequiredArgsConstructor
public class EmailCreateValidator implements ConstraintValidator<EmailCreateInfo, EmailCreateDto> {

    private final EmailRepository emailRepository;

    @Override
    public boolean isValid(EmailCreateDto emailCreateDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return (StringUtils.hasText(emailCreateDto.getCreateEmail()) &&
                emailCreateDto.getCreateEmail().contains("@") &&
                emailRepository.findEmail(emailCreateDto.getCreateEmail()).isEmpty());
    }
}
