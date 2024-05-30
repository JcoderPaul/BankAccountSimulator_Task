package prod.oldboy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prod.oldboy.dto.PagePaginationResponse;
import prod.oldboy.dto.user_dto.UserReadDto;
import prod.oldboy.dto.user_dto.UserSearchCriteria;
import prod.oldboy.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Search engine for user data", description = "Methods for find user info")
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}/email")
    @Operation(summary = "Find user by email",
               description = "Get user with enter email (classical format)")
    public ResponseEntity<UserReadDto> findUserByEmail(@PathVariable("email") String email) {

        log.info("UserController: findUserByEmail method is start");

        ResponseEntity<UserReadDto> answer = userService.findUserByEmail(email)
                .map(content -> ResponseEntity.ok().body(content))
                .orElseGet(() -> ResponseEntity.notFound().build());

        log.info("UserController: findUserByEmail method is finished");

        return answer;
    }

    @GetMapping("/{phone}/phone")
    @Operation(summary = "Get user dy phone number",
               description = "Find user by phone number if it exists in base (min = 6, max = 12 digits)")
    public ResponseEntity<UserReadDto> findUserByPhone(@PathVariable("phone") String phone) {

        log.info("UserController: findUserByPhone method is start");

        ResponseEntity<UserReadDto> answer = userService.findUserByPhone(phone)
                .map(content -> ResponseEntity.ok().body(content))
                .orElseGet(() -> ResponseEntity.notFound().build());

        log.info("UserController: findUserByPhone method is finished");

        return answer;
    }

    @GetMapping("/{birthData}/bd")
    @Operation(summary = "Find all users by birthday (non pageable)",
               description = "Find all users by birth day filter without pagination (format yyyy-mm-dd)")
    public ResponseEntity<List<UserReadDto>> findUserByBirthDay(@PathVariable("birthData") LocalDate birthDate) {

        log.info("UserController: findUserByBirthDay method is start");

        ResponseEntity<List<UserReadDto>> answer = userService.findUserByBirthDate(birthDate)
                .map(content -> ResponseEntity.ok().body(content))
                .orElseGet(() -> ResponseEntity.notFound().build());

        log.info("UserController: findUserByBirthDay method is finished");

        return answer;
    }

    @GetMapping("/{birthData}/bd/pageable/")
    @Operation(summary = "Find all users by birthday (pageable)",
               description = "Find all users by birthday filter with pagination (format yyyy-mm-dd)")
    public ResponseEntity<PagePaginationResponse<UserReadDto>> findUserByBirthDay(@PathVariable("birthData")
                                                                                  LocalDate birthDate,
                                                                                  @RequestBody
                                                                                  Pageable pageable) {

        log.info("UserController: findUserByBirthDay (pageable) method is start");

        ResponseEntity<PagePaginationResponse<UserReadDto>> answer = ResponseEntity.status(HttpStatus.OK)
                .body(PagePaginationResponse.of(userService.findUserByBirthDate(birthDate, pageable)));

        log.info("UserController: findUserByBirthDay (pageable) method is finished");

        return answer;
    }

    @GetMapping("/{lastName}/ln/{firstName}/fn/{patronymic}/pr")
    @Operation(summary = "Find all users by names (not pageable)",
               description = "Find all users by names filter without pagination (format min = 1, max = 64 letters)")
    public ResponseEntity<List<UserReadDto>> findUserByNames(@PathVariable("lastName") String lastName,
                                                             @PathVariable("firstName") String firstName,
                                                             @PathVariable("patronymic") String patronymic) {

        log.info("UserController: findUserByNames method is start");

        ResponseEntity<List<UserReadDto>> answer = userService.findUserByNames(lastName, firstName, patronymic)
                .map(content -> ResponseEntity.ok().body(content))
                .orElseGet(() -> ResponseEntity.notFound().build());

        log.info("UserController: findUserByNames method is finished");

        return answer;
    }

    @GetMapping(value = "/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find all users by full names (pageable)",
               description = "Find all users by full names filter with pagination (format min = 1, max = 64 letters)")
    public ResponseEntity<PagePaginationResponse<UserReadDto>> findUserWithFilter(UserSearchCriteria filter,
                                                                                  Pageable pageable){

        log.info("UserController: findUserWithFilter (pageable) method is start");

        ResponseEntity<PagePaginationResponse<UserReadDto>> answer =
                ResponseEntity.status(HttpStatus.OK)
                        .body(PagePaginationResponse.of(userService.findAllWithFilter(filter, pageable)));

        log.info("UserController: findUserWithFilter (pageable) method is finished");

        return answer;
    }
}