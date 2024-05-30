package prod.oldboy.repository.amount_repo;

import prod.oldboy.database.entity.Amount;

import java.util.List;

public interface FilterAmountRepository {

    void updateAllAmountsDeposit(List<Amount> amountList);

}
