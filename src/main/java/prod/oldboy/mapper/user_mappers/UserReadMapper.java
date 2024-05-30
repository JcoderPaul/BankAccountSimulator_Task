package prod.oldboy.mapper.user_mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prod.oldboy.database.entity.User;
import prod.oldboy.dto.amount_dto.AmountReadDto;
import prod.oldboy.dto.email_dto.EmailReadDto;
import prod.oldboy.dto.phone_dto.PhoneReadDto;
import prod.oldboy.dto.user_dto.UserReadDto;
import prod.oldboy.mapper.AmountReadMapper;
import prod.oldboy.mapper.email_mappers.EmailReadMapper;
import prod.oldboy.mapper.Mapper;
import prod.oldboy.mapper.phone_mappers.PhoneReadMapper;
import prod.oldboy.repository.amount_repo.AmountRepository;
import prod.oldboy.repository.email_repo.EmailRepository;
import prod.oldboy.repository.phone_repo.PhoneRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final EmailRepository emailRepository;
    private final EmailReadMapper emailReadMapper;
    private final PhoneRepository phoneRepository;
    private final PhoneReadMapper phoneReadMapper;
    private final AmountRepository amountRepository;
    private final AmountReadMapper amountReadMapper;

    @Override
    public UserReadDto map(User object) {

        List<EmailReadDto> emailReadDtoList =
                emailRepository.findEmailByUserId(object.getId()).stream()
                        .map(email -> emailReadMapper.map(Optional.of(email)).get()).toList();

        List<PhoneReadDto> phoneReadDtoList =
                phoneRepository.findPhoneByUserId(object.getId()).stream()
                        .map(phone -> phoneReadMapper.map(Optional.of(phone)).get()).toList();

        AmountReadDto amountReadDto =
                amountReadMapper.map(amountRepository
                        .findAmountByAccountId(object.getAccount().getAccountId())).get();

        return new UserReadDto(
                object.getId(),
                object.getLastname(),
                object.getFirstname(),
                object.getPatronymic(),
                object.getBirthDate(),
                emailReadDtoList,
                phoneReadDtoList,
                amountReadDto
        );
    }
}
