package prod.oldboy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prod.oldboy.database.entity.Account;
import prod.oldboy.database.entity.Email;
import prod.oldboy.database.entity.User;
import prod.oldboy.dto.email_dto.EmailCreateDto;
import prod.oldboy.dto.email_dto.EmailReadDto;
import prod.oldboy.dto.email_dto.EmailUpdateDto;
import prod.oldboy.mapper.email_mappers.EmailReadMapper;
import prod.oldboy.repository.account_repo.AccountRepository;
import prod.oldboy.repository.email_repo.EmailRepository;
import prod.oldboy.repository.user_repo.UserRepository;
import prod.oldboy.security.JwtLoginEntity;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {

    private final EmailRepository emailRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private final EmailReadMapper emailReadMapper;

    @Transactional
    public boolean delete(String email) {
        
        log.info("EmailService: delete method is start");

        boolean emailDeleted = false;
        Optional<Email> mayBeEmail= emailRepository.findEmail(email);
        if(mayBeEmail.isPresent()){
            if(mayBeEmail.get().getUser().getId().equals(getThisAccount().getUser().getId())) {
                emailDeleted = deleteSpecificEmail(mayBeEmail.get());
            } else {
                log.warn("EmailService delete: Trying to delete someone else's email!");
            }
        } else {
            log.warn("EmailService delete: Email not found!");
        }
        log.info("EmailService: delete method is finished");
        return emailDeleted;
    }

    @Transactional
    public Optional<EmailReadDto> update(EmailUpdateDto emailUpdateDto){

        log.info("EmailService: update method is start");
        
        Optional<EmailReadDto> mayBeUpdateEmail = Optional.empty();
        Optional<Email> oldEmailIsHere = emailRepository.findEmail(emailUpdateDto.getOldEmail());
        Optional<Email> newEmailIsUnique = emailRepository.findEmail(emailUpdateDto.getNewEmail());
        if(oldEmailIsHere.isPresent() && newEmailIsUnique.isEmpty()){
            if(oldEmailIsHere.get().getUser().getId().equals(getThisAccount().getUser().getId())) {
                Email changeEmail = oldEmailIsHere.get();
                      changeEmail.setUserEmail(emailUpdateDto.getNewEmail());
                emailRepository.saveAndFlush(changeEmail);
                mayBeUpdateEmail = emailReadMapper.map(Optional.of(changeEmail));
            } else {
                mayBeUpdateEmail = emailReadMapper.map(oldEmailIsHere);
                log.warn("EmailService update: Trying to update someone else's email!");
            }
        } else {
            log.warn("EmailService update: Email not found or new email already exist!");
        }
        log.info("EmailService: update method is finished");
        return mayBeUpdateEmail;
    }

    @Transactional
    public Optional<EmailReadDto> create(EmailCreateDto emailCreateDto){

        log.info("EmailService create: create method is start");

        Optional<Email> newEmail = Optional.empty();
        User user = userRepository.findUserByAccountId(getThisAccount().getAccountId());
        if(emailRepository.findEmail(emailCreateDto.getCreateEmail()).isEmpty()){
            emailRepository.saveAndFlush(new Email(user, emailCreateDto.getCreateEmail()));
            newEmail = emailRepository.findEmail(emailCreateDto.getCreateEmail());
        } else {
            log.warn("EmailService create: Such an email exists!");
        }
        log.info("EmailService create: create method is finished");
        return emailReadMapper.map(newEmail);
    }

    private boolean deleteSpecificEmail(Email email){
        boolean delete = false;
        Long userId = email.getUser().getId();
        if (emailRepository.findEmailByUserId(userId).size() > 1){
            emailRepository.delete(email);
            delete = true;
            log.warn("Email deleted!");
        } else {
            log.warn("You can not delete last email!");
        }
        return delete;
    }

    private Account getThisAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepository.findById(((JwtLoginEntity) authentication.getPrincipal()).getId()).get();
    }
}
