package prod.oldboy.dto.user_dto;

import java.time.LocalDate;

public record UserSearchCriteria(String lastname,
                                 String firstname,
                                 String patronymic,
                                 LocalDate birthDate) {
}
