package prod.oldboy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prod.oldboy.dto.account_dto.AccountCreateDto;
import prod.oldboy.dto.user_dto.UserReadDto;
import prod.oldboy.service.AccountService;
import prod.oldboy.validation.group.CreateAction;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Registration", description = "Method for registration account (full user info)")
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create account (enter full user and amount data)",
               description = "Open access for all users (birthday format: yyyy-mm-dd, amount format: xxx.xx)")
    public ResponseEntity<UserReadDto> create(@Validated(CreateAction.class)
                                              @RequestBody
                                              AccountCreateDto accountCreateDto) {

        log.info("AccountController: create method is start");

        ResponseEntity<UserReadDto> responseEntity =
                ResponseEntity.status(HttpStatus.CREATED)
                              .body(accountService.create(accountCreateDto));

        log.info("AccountController: create method is finished");

        return responseEntity;
    }
}