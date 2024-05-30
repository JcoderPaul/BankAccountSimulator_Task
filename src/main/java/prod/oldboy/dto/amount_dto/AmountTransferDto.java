package prod.oldboy.dto.amount_dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import prod.oldboy.validation.annotations.transfer.TransferAmountInfo;
import prod.oldboy.validation.group.UpdateAction;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TransferAmountInfo(groups = {UpdateAction.class})
public class AmountTransferDto {
    @NotNull
    Long accountTransferTo;

    @Min(value = 0)
    @Digits(integer = 14, fraction = 2)
    BigDecimal amountTransfer;
}
