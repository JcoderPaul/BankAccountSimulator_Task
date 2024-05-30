package prod.oldboy.validation.impl.phone_validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import prod.oldboy.dto.phone_dto.PhoneCreateDto;
import prod.oldboy.repository.phone_repo.PhoneRepository;
import prod.oldboy.validation.annotations.phone.PhoneCreateInfo;

@Component
@RequiredArgsConstructor
public class PhoneCreateValidator implements ConstraintValidator<PhoneCreateInfo,
        PhoneCreateDto> {

    private final PhoneRepository phoneRepository;

    @Override
    public boolean isValid(PhoneCreateDto phoneCreateDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return (StringUtils.hasText(phoneCreateDto.getCreatePhone()) &&
                phoneRepository.findUserPhone(phoneCreateDto.getCreatePhone()).isEmpty() &&
                (phoneCreateDto.getCreatePhone().length() > 3 &&
                        phoneCreateDto.getCreatePhone().length() < 12));
    }
}
