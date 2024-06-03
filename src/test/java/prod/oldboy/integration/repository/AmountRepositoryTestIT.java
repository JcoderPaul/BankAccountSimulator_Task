package prod.oldboy.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import prod.oldboy.database.entity.Amount;
import prod.oldboy.integration.IntegrationTestBase;
import prod.oldboy.repository.amount_repo.AmountRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.math.RoundingMode.HALF_DOWN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
class AmountRepositoryTestIT extends IntegrationTestBase {

    private final AmountRepository amountRepository;

    @Test
    void checkFindAllTest() {
        List<Amount> amounts = amountRepository.findAll();
        assertThat(amounts).hasSize(9);
    }

    @Test
    void findAmountByAccountIdTest() {
        Optional<Amount> existAmount = amountRepository.findAmountByAccountId(1L);

        assertTrue(existAmount.isPresent());
        existAmount.ifPresent(amount -> assertEquals(1L, amount.getAccount().getAccountId()));

        Optional<Amount> notExistAmount = amountRepository.findAmountByAccountId(15L);

        assertTrue(notExistAmount.isEmpty());
    }

    @Test
    void updateAllAmountsDepositTest() {
        List<Amount> currentAmounts = amountRepository.findAll();
        Map<Long, BigDecimal> unchangedAmounts = new HashMap<>();
        BigDecimal amountIncrease = BigDecimal.valueOf(50);

        for (Amount amount: currentAmounts) {
            unchangedAmounts.put(amount.getId(), amount.getAmount());
            amount.setAmount(amount.getAmount().add(amountIncrease));
        }

        amountRepository.updateAllAmountsDeposit(currentAmounts);

        for (long i = 0; i < unchangedAmounts.size(); i++) {
            assertEquals(currentAmounts.get((int) i).getAmount().setScale(2,HALF_DOWN),
                         unchangedAmounts.get(i + 1L).add(amountIncrease).setScale(2,HALF_DOWN));
        }
    }
}