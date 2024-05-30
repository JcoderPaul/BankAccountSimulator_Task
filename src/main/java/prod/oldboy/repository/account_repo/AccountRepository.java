package prod.oldboy.repository.account_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prod.oldboy.database.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);

    @Query(value = "SELECT accounts.* " +
            "FROM accounts " +
            "WHERE accounts.login = :login",
            nativeQuery = true)
    Optional<Account> findByLogin(String login);
}
