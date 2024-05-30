package prod.oldboy.repository.amount_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prod.oldboy.database.entity.Amount;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmountRepository extends JpaRepository<Amount, Long>,
                                          FilterAmountRepository{

    List<Amount> findAll();

    @Query(value = "SELECT am.* " +
                   "FROM amounts AS am " +
                   "WHERE am.account_id =:id",
                   nativeQuery = true)
    Optional<Amount> findAmountByAccountId(Long id);
}
