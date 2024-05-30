package prod.oldboy.validation.impl.amount_validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prod.oldboy.dto.amount_dto.AmountTransferDto;
import prod.oldboy.repository.account_repo.AccountRepository;
import prod.oldboy.validation.annotations.transfer.TransferAmountInfo;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AmountTransferValidator implements ConstraintValidator<TransferAmountInfo,
        AmountTransferDto> {

    private final AccountRepository accountRepository;

    @Override
    public boolean isValid(AmountTransferDto amountTransferDto,
                           ConstraintValidatorContext constraintValidatorContext) {
        return (amountTransferDto.getAmountTransfer().compareTo(BigDecimal.valueOf(0)) > 0 &&
                accountRepository.findById(amountTransferDto.getAccountTransferTo()).isPresent());
    }
}
