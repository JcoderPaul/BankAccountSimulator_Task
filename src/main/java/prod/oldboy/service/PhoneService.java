package prod.oldboy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prod.oldboy.database.entity.Account;
import prod.oldboy.database.entity.Phone;
import prod.oldboy.database.entity.User;
import prod.oldboy.dto.phone_dto.PhoneCreateDto;
import prod.oldboy.dto.phone_dto.PhoneReadDto;
import prod.oldboy.dto.phone_dto.PhoneUpdateDto;
import prod.oldboy.mapper.phone_mappers.PhoneReadMapper;
import prod.oldboy.repository.account_repo.AccountRepository;
import prod.oldboy.repository.phone_repo.PhoneRepository;
import prod.oldboy.repository.user_repo.UserRepository;
import prod.oldboy.security.JwtLoginEntity;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final AccountRepository accountRepository;
    private final PhoneReadMapper phoneReadMapper;
    private final UserRepository userRepository;

    @Transactional
    public boolean delete(String phone) {

        log.info("PhoneService: delete method is start");

        boolean phoneDeleted = false;
        Optional<Phone> mayBePhone = phoneRepository.findUserPhone(phone);
        if (mayBePhone.isPresent()) {
            if(mayBePhone.get().getUser().getId().equals(getThisAccount().getUser().getId())) {
                phoneDeleted = deleteSpecificPhone(mayBePhone.get());
            } else {
                log.warn("PhoneService delete: Trying to delete someone else's email!");
            }
        } else {
            log.warn("PhoneService delete: Email not found!");
        }
        log.info("PhoneService: delete method is finished");
        return phoneDeleted;
    }

    @Transactional
    public Optional<PhoneReadDto> update(PhoneUpdateDto phoneUpdateDto) {

        log.info("PhoneService: update method is start");

        Optional<PhoneReadDto> mayByUpdatePhone = Optional.empty();
        Optional<Phone> oldPhoneIsHere = phoneRepository.findUserPhone(phoneUpdateDto.getOldPhone());
        Optional<Phone> newPhoneIsUnique = phoneRepository.findUserPhone(phoneUpdateDto.getNewPhone());
        if (oldPhoneIsHere.isPresent() && newPhoneIsUnique.isEmpty()) {
            if(oldPhoneIsHere.get().getUser().getId().equals(getThisAccount().getUser().getId())) {
                Phone changePhone = oldPhoneIsHere.get();
                      changePhone.setUserPhone(phoneUpdateDto.getNewPhone());
                phoneRepository.saveAndFlush(changePhone);
                mayByUpdatePhone = phoneReadMapper.map(Optional.of(changePhone));
            }else {
                log.warn("PhoneService update: Trying to update someone else's phone!");
                mayByUpdatePhone = phoneReadMapper.map(oldPhoneIsHere);
            }
        } else {
            log.warn("PhoneService update: Phone not found or new phone already exist!");
        }
        log.info("PhoneService: update method is finished");
        return mayByUpdatePhone;
    }

    @Transactional
    public Optional<PhoneReadDto> create(PhoneCreateDto phoneCreateDto){

        log.info("PhoneService create: create method is start");

        Optional<Phone> newPhone = Optional.empty();
        User user = userRepository.findUserByAccountId(getThisAccount().getAccountId());
        if(phoneRepository.findUserPhone(phoneCreateDto.getCreatePhone()).isEmpty()){
            phoneRepository.saveAndFlush(new Phone(user, phoneCreateDto.getCreatePhone()));
            newPhone = phoneRepository.findUserPhone(phoneCreateDto.getCreatePhone());
        } else {
            log.warn("PhoneService create: Such an phone exists!");
        }
        log.info("PhoneService create: create method is finished");
        return phoneReadMapper.map(newPhone);
    }

    private boolean deleteSpecificPhone(Phone phone) {
        boolean delete = false;
        Long userId = phone.getUser().getId();
        if (phoneRepository.findPhoneByUserId(userId).size() > 1) {
            phoneRepository.delete(phone);
            delete = true;
            log.warn("Phone deleted!");
        } else {
            log.warn("You can not delete last phone!");
        }
        return delete;
    }

    private Account getThisAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return accountRepository.findById(((JwtLoginEntity) authentication.getPrincipal()).getId()).get();
    }
}