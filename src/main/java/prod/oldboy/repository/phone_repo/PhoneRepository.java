package prod.oldboy.repository.phone_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prod.oldboy.database.entity.Phone;
import prod.oldboy.repository.user_repo.FilterUserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long>,
                                         FilterUserRepository {

    @Query(value = "SELECT pn.* " +
                   "FROM phones AS pn " +
                   "WHERE pn.user_id = :id",
                   nativeQuery = true)
    List<Phone> findPhoneByUserId(Long id);

    @Query(value = "SELECT pn.* " +
                   "FROM phones AS pn " +
                   "WHERE pn.user_phone = :phone",
                   nativeQuery = true)
    Optional<Phone> findUserPhone(String phone);
}
