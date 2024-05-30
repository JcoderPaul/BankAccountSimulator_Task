package prod.oldboy.repository.user_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prod.oldboy.database.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
                                        FilterUserRepository {

    @Query(value = "SELECT u.* " +
            "FROM emails AS e " +
            "JOIN users AS u " +
            "ON e.user_id = u.id " +
            "WHERE e.user_email = :email",
            nativeQuery = true)
    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT u.* " +
            "FROM phones AS ph " +
            "JOIN users AS u " +
            "ON ph.user_id = u.id " +
            "WHERE ph.user_phone = :phone",
            nativeQuery = true)
    Optional<User> findUserByPhone(String phone);

    @Query(value = "SELECT u.* " +
            "FROM users AS u " +
            "WHERE u.birth_date > :date",
            nativeQuery = true)
    Optional<List<User>> findUserByBirthDay(LocalDate date);

    @Query(value = "SELECT u.* " +
            "FROM users AS u " +
            "WHERE u.lastname like %:lastName% " +
            "AND u.firstname like %:firstName% " +
            "AND u.patronymic like %:patronymic%",
            nativeQuery = true)
    Optional<List<User>> findUserByNames(String lastName, String firstName, String patronymic);

    Optional<User> findById(Long userId);

    @Query(value = "SELECT u.* " +
            "FROM users AS u " +
            "WHERE u.account_id = :accountId",
            nativeQuery = true)
    User findUserByAccountId(Long accountId);
}
