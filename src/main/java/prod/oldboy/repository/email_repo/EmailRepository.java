package prod.oldboy.repository.email_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prod.oldboy.database.entity.Email;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query(value = "SELECT e.* " +
                   "FROM emails AS e " +
                   "WHERE e.user_id =:id",
                   nativeQuery = true)
    List<Email> findEmailByUserId(Long id);

    @Query(value = "SELECT e.* " +
                   "FROM emails AS e " +
                   "WHERE e.user_email =:email",
                   nativeQuery = true)
    Optional<Email> findEmail(String email);
}
