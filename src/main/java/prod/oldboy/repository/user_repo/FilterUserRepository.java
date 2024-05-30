package prod.oldboy.repository.user_repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import prod.oldboy.database.entity.User;
import prod.oldboy.dto.user_dto.UserSearchCriteria;

import java.time.LocalDate;

public interface FilterUserRepository {

    Page<User> findAllWithFilter(UserSearchCriteria filter, Pageable pageable);

    Page<User> findUserByBirthDayPageable(LocalDate date, Pageable pageable);

}
