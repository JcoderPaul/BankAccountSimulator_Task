package prod.oldboy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prod.oldboy.database.entity.Account;
import prod.oldboy.dto.jwt_dto.JwtAuthRequest;
import prod.oldboy.dto.jwt_dto.JwtAuthResponse;
import prod.oldboy.security.JwtTokenGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountService accountService;
    private final JwtTokenGenerator jwtTokenGenerator;

    public JwtAuthResponse login(JwtAuthRequest loginRequest) {

        log.info("AuthService: Login method is start");

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        Account account = accountService.findAccountByLogin(loginRequest.getLogin());
        jwtAuthResponse.setId(account.getAccountId());
        jwtAuthResponse.setLogin(account.getLogin());
        jwtAuthResponse.setAccessToken(jwtTokenGenerator.getToken(account.getAccountId(), account.getLogin()));

        log.info("AuthService: Login method is finished");

        return jwtAuthResponse;
    }
}
