package prod.oldboy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prod.oldboy.dto.user_dto.UserReadDto;
import prod.oldboy.dto.user_dto.UserSearchCriteria;
import prod.oldboy.mapper.user_mappers.UserReadMapper;
import prod.oldboy.repository.user_repo.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final UserReadMapper userReadMapper;

    public Optional<UserReadDto> findUserByEmail(String email) {
           return userRepository.findUserByEmail(email)
                .map(object -> userReadMapper.map(object));
    }

    public Optional<UserReadDto> findUserByPhone(String phone) {
        return userRepository.findUserByPhone(phone)
                .map(object -> userReadMapper.map(object));
    }

    public Optional<List<UserReadDto>> findUserByBirthDate(LocalDate date) {
        return Optional.of(userRepository.findUserByBirthDay(date).
                get().
                stream().
                map(user -> userReadMapper.map(user)).
                toList());
    }

    public Optional<List<UserReadDto>> findUserByNames(String lastName, String firstName, String patronymic) {
        return Optional.of(userRepository.findUserByNames(lastName, firstName, patronymic).
                get().
                stream().
                map(user -> userReadMapper.map(user)).
                toList());
    }

    public Page<UserReadDto> findUserByBirthDate(LocalDate date, Pageable pageable) {
        return userRepository.findUserByBirthDayPageable(date, pageable)
                .map(object -> userReadMapper.map(object));
    }

    public Page<UserReadDto> findAllWithFilter(UserSearchCriteria filter, Pageable pageable) {

        return userRepository.findAllWithFilter(filter, pageable)
                .map(object -> userReadMapper.map(object));
    }
}
