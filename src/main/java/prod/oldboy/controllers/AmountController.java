package prod.oldboy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prod.oldboy.dto.amount_dto.AmountTransferDto;
import prod.oldboy.service.AmountService;
import prod.oldboy.validation.group.UpdateAction;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/amounts")
@RequiredArgsConstructor
@Tag(name = "Money transfer", description = "Method for transfer money from logged in user to another")
public class AmountController {

    private final AmountService amountService;

    @PutMapping("/transfer")
    @Operation(summary = "Transfer money simulator",
               description = "Transfer from authenticated user to another user (amount format: ***.**)")
    public ResponseEntity<?> transferMoney(@Validated(UpdateAction.class)
                                           @RequestBody
                                           AmountTransferDto amountTransferDto) {

        log.info("AmountController: method transferMoney is start");

        ResponseEntity<?> mayBeTransfer = amountService.transferMoney(amountTransferDto)
                .map(content -> ResponseEntity.ok().body(content))
                .orElseGet(() -> ResponseEntity.noContent().build());

        log.info("AmountController: method transferMoney is finished");

        return mayBeTransfer;
    }
}
