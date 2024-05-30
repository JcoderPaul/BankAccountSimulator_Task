package prod.oldboy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prod.oldboy.database.entity.*;
import prod.oldboy.dto.account_dto.AccountCreateDto;
import prod.oldboy.dto.user_dto.UserReadDto;
import prod.oldboy.mapper.user_mappers.UserReadMapper;
import prod.oldboy.repository.account_repo.AccountRepository;
import prod.oldboy.repository.amount_repo.AmountRepository;
import prod.oldboy.repository.email_repo.EmailRepository;
import prod.oldboy.repository.phone_repo.PhoneRepository;
import prod.oldboy.repository.user_repo.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final AmountRepository amountRepository;
    private final UserReadMapper userReadMapper;

    @Transactional
    public UserReadDto create(AccountCreateDto accountCreateDto) {

        log.info("AccountService: create method is start");

        Account newAccount;
        if (accountRepository.findByLogin(accountCreateDto.getLogin()).isPresent() ||
                emailRepository.findEmail(accountCreateDto.getEmail()).isPresent() ||
                    phoneRepository.findUserPhone(accountCreateDto.getPhone()).isPresent()){
            throw new IllegalArgumentException("Login/Phone/Email already exists!");
        } else {
            newAccount = new Account(accountCreateDto.getLogin(),
                                     passwordEncoder.encode(accountCreateDto.getPass()));
            accountRepository.saveAndFlush(newAccount);
        }
        User newUser = new User(newAccount,
                accountCreateDto.getLastname(),
                accountCreateDto.getFirstname(),
                accountCreateDto.getPatronymic(),
                accountCreateDto.getBirthDate());
        userRepository.saveAndFlush(newUser);
        emailRepository.saveAndFlush(new Email(newUser, accountCreateDto.getEmail()));
        phoneRepository.saveAndFlush(new Phone(newUser, accountCreateDto.getPhone()));
        amountRepository.saveAndFlush(new Amount(newAccount, accountCreateDto.getAmount()));

        log.info("AccountService: create method is finished");

        return userReadMapper.map(newUser);
    }

    public Account findAccountByLogin(String login) {
        return accountRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Login not found."));
    }
}
