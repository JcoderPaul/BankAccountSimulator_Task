package prod.oldboy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prod.oldboy.database.entity.Account;
import prod.oldboy.database.entity.Amount;
import prod.oldboy.dto.amount_dto.AmountReadDto;
import prod.oldboy.dto.amount_dto.AmountTransferDto;
import prod.oldboy.mapper.AmountReadMapper;
import prod.oldboy.repository.account_repo.AccountRepository;
import prod.oldboy.repository.amount_repo.AmountRepository;
import prod.oldboy.security.JwtLoginEntity;
import prod.oldboy.utils.Interests;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
@Transactional
public class AmountService {

    private final AmountRepository amountRepository;
    private final AccountRepository accountRepository;
    private final AmountReadMapper amountReadMapper;

    private BigDecimal increase = Interests.getInstance().getIncreaseBy();

    @Scheduled(cron = "0 * * * * *")
    public void calculationOfInterest(){

        log.info("AmountService calculationOfInterest: method is start");

        List<Amount> amountList;
        int hasChanges = 0;
        synchronized (this) {
            amountList = amountRepository.findAll();
            for (Amount amount : amountList) {
                BigDecimal thisStartLimit = amount.getStartLimit();
                BigDecimal lastInterest = amount.getInterest();
                Integer lastPeriod = amount.getCountPeriod();
                if (0 > lastInterest.compareTo(amount.getStopLimit())) {
                    Integer currentPeriod = lastPeriod + 1;
                    BigDecimal currentInterest = (thisStartLimit
                                    .multiply(increase.pow(currentPeriod)))
                                    .subtract(thisStartLimit);
                    BigDecimal moneyToMainAmount = currentInterest.subtract(lastInterest);
                    amount.setAmount(amount.getAmount().add(moneyToMainAmount));
                    amount.setCountPeriod(currentPeriod);
                    amount.setInterest(currentInterest);
                    hasChanges++;
                }
            }
            if (hasChanges > 0) {
                log.warn("AmountService calculationOfInterest: interest arrived Ura!");
                amountRepository.updateAllAmountsDeposit(amountList);
            } else {
                log.error("AmountService calculationOfInterest: the fucking bank squeezed our interest!");
            }
        }
        log.info("AmountService calculationOfInterest: method is finished");
    }

    public Optional<AmountReadDto> transferMoney(AmountTransferDto amountTransferDto) {

        log.info("AmountService transferMoney: method is start");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account accountIdFrom =
                accountRepository.findById(((JwtLoginEntity) authentication.getPrincipal()).getId()).get();
        Optional<Amount> moneyFrom = amountRepository.findAmountByAccountId(accountIdFrom.getAccountId());
        Optional<Amount> moneyTo = amountRepository.findAmountByAccountId(amountTransferDto.getAccountTransferTo());
            if(moneyFrom.isPresent() && moneyTo.isPresent()) {
                synchronized (this) {
                    if (0 < moneyFrom.get().getAmount()
                                    .subtract(amountTransferDto.getAmountTransfer())
                                                      .compareTo(BigDecimal.valueOf(0))) {
                        Amount saveChangeFrom = moneyFrom.get();
                        saveChangeFrom.setAmount(moneyFrom.get()
                                .getAmount()
                                .subtract(amountTransferDto.getAmountTransfer()));
                        Amount saveChangeTo = moneyTo.get();
                        saveChangeTo.setAmount(moneyTo.get()
                                .getAmount()
                                .add(amountTransferDto.getAmountTransfer()));
                        amountRepository.saveAndFlush(saveChangeFrom);
                        amountRepository.saveAndFlush(saveChangeTo);
                    }
                }
            } else {
                log.error("Money transfer failed, destination account not found.");
            }
        log.info("AmountService transferMoney: method is finished");
        return amountReadMapper.map(moneyFrom);
    }
}