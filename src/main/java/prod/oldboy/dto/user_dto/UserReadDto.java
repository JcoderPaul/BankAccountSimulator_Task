package prod.oldboy.dto.user_dto;

import lombok.Value;
import prod.oldboy.dto.amount_dto.AmountReadDto;
import prod.oldboy.dto.email_dto.EmailReadDto;
import prod.oldboy.dto.phone_dto.PhoneReadDto;

import java.time.LocalDate;
import java.util.List;

@Value
public class UserReadDto {

    Long id;
    String lastname;
    String firstname;
    String patronymic;
    LocalDate birthDate;
    List<EmailReadDto> emailList;
    List<PhoneReadDto> phoneList;
    AmountReadDto amountDto;

}

