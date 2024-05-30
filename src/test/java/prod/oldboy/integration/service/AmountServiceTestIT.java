package prod.oldboy.integration.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import prod.oldboy.database.entity.Amount;
import prod.oldboy.dto.amount_dto.AmountReadDto;
import prod.oldboy.dto.amount_dto.AmountTransferDto;
import prod.oldboy.integration.IntegrationTestBase;
import prod.oldboy.repository.amount_repo.AmountRepository;
import prod.oldboy.security.JwtLoginEntity;
import prod.oldboy.service.AccountService;
import prod.oldboy.service.AmountService;
import prod.oldboy.utils.Interests;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.RoundingMode.HALF_DOWN;

@RequiredArgsConstructor
public class AmountServiceTestIT extends IntegrationTestBase {

    private static final Long SENDER_MONEY_ACCOUNT_ID = 1L;
    private static final String SENDER_MONEY_LOGIN = "Gena12";
    private static final String SENDER_MONEY_PASSWORD = "111111";
    private static final Long RECEIVER_MONEY_ACCOUNT_ID = 2L;
    private static final String RECEIVER_MONEY_MONEY_LOGIN = "Cheburator";
    private static final BigDecimal SUFFICIENT_SUM_FOR_TRANSFER = BigDecimal.valueOf(100.0);
    private static final BigDecimal OVER_LIMIT_SUM_FOR_TRANSFER = BigDecimal.valueOf(5000.0);
    private static final Long NOT_VALID_ACCOUNT_ID = 45L;

    private final AmountRepository amountRepository;
    private final AmountService amountService;
    private final AccountService accountService;
    private AmountTransferDto testAmountTransferDto = new AmountTransferDto();
    private BigDecimal increase = Interests.getInstance().getIncreaseBy();

    @BeforeEach
    public void setup() {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        new JwtLoginEntity(SENDER_MONEY_ACCOUNT_ID,
                                           SENDER_MONEY_LOGIN,
                                           SENDER_MONEY_PASSWORD), "");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void transferMoneySuccessTest() {
        BigDecimal sendersOldAmount =
                accountService.findAccountByLogin(SENDER_MONEY_LOGIN).getAmount().getAmount();
        BigDecimal receiversOldAmount =
                accountService.findAccountByLogin(RECEIVER_MONEY_MONEY_LOGIN).getAmount().getAmount();

        testAmountTransferDto.setAmountTransfer(SUFFICIENT_SUM_FOR_TRANSFER);
        testAmountTransferDto.setAccountTransferTo(RECEIVER_MONEY_ACCOUNT_ID);


        Optional<AmountReadDto> mayBySenderNewAmount =
                amountService.transferMoney(testAmountTransferDto);
        BigDecimal sendersNewAmount =
                accountService.findAccountByLogin(SENDER_MONEY_LOGIN).getAmount().getAmount();
        BigDecimal receiversNewAmount =
                accountService.findAccountByLogin(RECEIVER_MONEY_MONEY_LOGIN).getAmount().getAmount();

        Assertions.assertThat(mayBySenderNewAmount.isPresent()).isTrue();
        Assertions.assertThat(mayBySenderNewAmount.get().amount()).
                isEqualTo(sendersOldAmount.subtract(SUFFICIENT_SUM_FOR_TRANSFER));
        Assertions.assertThat(sendersNewAmount).
                isEqualTo(sendersOldAmount.subtract(SUFFICIENT_SUM_FOR_TRANSFER));
        Assertions.assertThat(receiversNewAmount)
                .isEqualTo(receiversOldAmount.add(SUFFICIENT_SUM_FOR_TRANSFER));
    }

    @Test
    void transferMoneyFailedWithNotValidIdTest() {

        testAmountTransferDto.setAmountTransfer(SUFFICIENT_SUM_FOR_TRANSFER);
        testAmountTransferDto.setAccountTransferTo(NOT_VALID_ACCOUNT_ID);

        BigDecimal sendersOldAmount =
                accountService.findAccountByLogin(SENDER_MONEY_LOGIN).getAmount().getAmount();

        Optional<AmountReadDto> thisTestTransferResult =
                amountService.transferMoney(testAmountTransferDto);

        BigDecimal sendersNewAmount =
                accountService.findAccountByLogin(SENDER_MONEY_LOGIN).getAmount().getAmount();

        Assertions.assertThat(thisTestTransferResult.get().amount()).isEqualTo(sendersOldAmount);
        Assertions.assertThat(sendersOldAmount).isEqualTo(sendersNewAmount);
    }

    @Test
    void transferMoneyFailedWithExtraAmountSum() {

        testAmountTransferDto.setAmountTransfer(OVER_LIMIT_SUM_FOR_TRANSFER);
        testAmountTransferDto.setAccountTransferTo(RECEIVER_MONEY_ACCOUNT_ID);

        BigDecimal sendersOldAmount =
                accountService.findAccountByLogin(SENDER_MONEY_LOGIN).getAmount().getAmount();
        BigDecimal receiversOldAmount =
                accountService.findAccountByLogin(RECEIVER_MONEY_MONEY_LOGIN).getAmount().getAmount();

        Optional<AmountReadDto> thisTestTransferResult =
                amountService.transferMoney(testAmountTransferDto);

        BigDecimal sendersNewAmount =
                accountService.findAccountByLogin(SENDER_MONEY_LOGIN).getAmount().getAmount();
        BigDecimal receiversNewAmount =
                accountService.findAccountByLogin(RECEIVER_MONEY_MONEY_LOGIN).getAmount().getAmount();

        Assertions.assertThat(thisTestTransferResult.get().amount()).isEqualTo(sendersOldAmount);
        Assertions.assertThat(sendersOldAmount).isEqualTo(sendersNewAmount);
        Assertions.assertThat(receiversOldAmount).isEqualTo(receiversNewAmount);
    }

    @Test
    void calculationOfInterestTest(){
        Long userTestId = 4L;
        Amount userTestAmount = amountRepository.findAmountByAccountId(userTestId).get();
        BigDecimal startDeposit = userTestAmount.getAmount();

        amountService.calculationOfInterest();

        BigDecimal userDepositAfterOneIterationOfMethod =
                userTestAmount.getAmount().setScale(2, HALF_DOWN);
        Integer userCountPeriodAfterOneIterationOfMethod = userTestAmount.getCountPeriod();

        Assertions.assertThat(userCountPeriodAfterOneIterationOfMethod).isEqualTo(1);
        Assertions.assertThat(userDepositAfterOneIterationOfMethod)
                .isEqualTo(startDeposit.multiply(increase.pow(1)).setScale(2,HALF_DOWN));

        for(int i = 0; i < 6; i++){
            amountService.calculationOfInterest();
        }

        BigDecimal userDepositAfterSevenIterationOfMethod =
                userTestAmount.getAmount().setScale(2,HALF_DOWN);
        Integer userCountPeriodAfterSevenIterationOfMethod = userTestAmount.getCountPeriod();

        Assertions.assertThat(userCountPeriodAfterSevenIterationOfMethod).isEqualTo(7);
        Assertions.assertThat(userDepositAfterSevenIterationOfMethod)
                .isEqualTo(startDeposit.multiply(increase.pow(7)).setScale(2,HALF_DOWN));

        for(int i = 0; i < 30; i++){
            amountService.calculationOfInterest();
        }

        BigDecimal userDepositAfter37IterationOfMethod =
                userTestAmount.getAmount().setScale(2,HALF_DOWN);
        Integer userCountPeriodAfter37IterationOfMethod = userTestAmount.getCountPeriod();

        Assertions.assertThat(userCountPeriodAfter37IterationOfMethod).isNotEqualTo(37);
        Assertions.assertThat(userDepositAfter37IterationOfMethod)
                .isNotEqualTo(startDeposit.multiply(increase.pow(37)).setScale(2,HALF_DOWN));
    }
}
