package prod.oldboy.validation.impl.account_validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import prod.oldboy.dto.account_dto.AccountCreateDto;
import prod.oldboy.repository.account_repo.AccountRepository;
import prod.oldboy.repository.email_repo.EmailRepository;
import prod.oldboy.repository.phone_repo.PhoneRepository;
import prod.oldboy.validation.annotations.account.CreateAccountInfo;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CreateAccountValidator implements ConstraintValidator<CreateAccountInfo, AccountCreateDto> {

    private final AccountRepository accountRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    @Override
    public boolean isValid(AccountCreateDto value, ConstraintValidatorContext context) {

        boolean allFieldsNotEmpty = (StringUtils.hasText(value.getFirstname()) &&
                                     StringUtils.hasText(value.getLastname()) &&
                                     StringUtils.hasText(value.getPatronymic()) &&
                                     StringUtils.hasText(value.getEmail()) &&
                                     StringUtils.hasText(value.getPhone()) &&
                                     emailRepository.findEmail(value.getEmail()).isEmpty() &&
                                     phoneRepository.findUserPhone(value.getPhone()).isEmpty() &&
                                     StringUtils.hasText(value.getPass()) &&
                                     StringUtils.hasText(value.getLogin()) &&
                                     accountRepository.findByLogin(value.getLogin()).isEmpty() &&
                                     value.getBirthDate().isBefore(LocalDate.now()) &&
                                     value.getAmount().compareTo(BigDecimal.valueOf(0)) > 0 &&
                                     (value.getPhone().length() > 3 &&
                                             value.getPhone().length() < 12) &&
                                     value.getEmail().contains("@"));
        return allFieldsNotEmpty;
    }
}
