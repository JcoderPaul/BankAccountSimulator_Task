package prod.oldboy.validation.impl.phone_validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import prod.oldboy.dto.phone_dto.PhoneUpdateDto;
import prod.oldboy.repository.phone_repo.PhoneRepository;
import prod.oldboy.validation.annotations.phone.PhoneUpdateInfo;

@Component
@RequiredArgsConstructor
public class PhoneUpdateValidator implements ConstraintValidator<PhoneUpdateInfo,
        PhoneUpdateDto> {

    private final PhoneRepository phoneRepository;

    @Override
    public boolean isValid(PhoneUpdateDto phoneUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
        return (StringUtils.hasText(phoneUpdateDto.getOldPhone()) &&
                StringUtils.hasText(phoneUpdateDto.getNewPhone()) &&
                phoneRepository.findUserPhone(phoneUpdateDto.getOldPhone()).isPresent() &&
                phoneRepository.findUserPhone(phoneUpdateDto.getNewPhone()).isEmpty() &&
                (phoneUpdateDto.getNewPhone().length() > 3 &&
                        phoneUpdateDto.getNewPhone().length() < 12));
    }
}
