package prod.oldboy.repository.amount_repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import prod.oldboy.database.entity.Amount;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class FilterAmountRepositoryImpl implements FilterAmountRepository{

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String UPDATE_DEPOSIT_NAMED = """
        UPDATE amounts
        SET amount = :amount
        WHERE id = :id
        """;

    @Override
    public void updateAllAmountsDeposit(List<Amount> amountList) {
        MapSqlParameterSource[] args = amountList
                .stream()
                .map(amount -> Map.of(
                        "id", amount.getId(),
                        "amount", amount.getAmount()
                ))
                .map(values -> new MapSqlParameterSource(values))
                .toArray(value -> new MapSqlParameterSource[value]);
        namedJdbcTemplate.batchUpdate(UPDATE_DEPOSIT_NAMED, args);
    }
}
