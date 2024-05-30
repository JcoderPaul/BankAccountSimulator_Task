package prod.oldboy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prod.oldboy.dto.jwt_dto.JwtAuthRequest;
import prod.oldboy.dto.jwt_dto.JwtAuthResponse;
import prod.oldboy.service.AuthService;
import prod.oldboy.validation.group.LogInAction;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Method for authenticates account (enter login and password)")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Log in to the system",
            description = "Log in to the system and receive AccessToken")
    public JwtAuthResponse login(@Validated(LogInAction.class)
                                 @RequestBody JwtAuthRequest loginRequest) {

        log.info("AuthController: login method is start");

        JwtAuthResponse response = authService.login(loginRequest);

        log.info("AuthController: login method is finished:" + response);

        return response;
    }
}

