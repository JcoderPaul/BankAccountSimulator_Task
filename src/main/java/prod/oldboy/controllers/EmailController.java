package prod.oldboy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prod.oldboy.dto.email_dto.EmailCreateDto;
import prod.oldboy.dto.email_dto.EmailUpdateDto;
import prod.oldboy.service.EmailService;
import prod.oldboy.validation.group.CreateAction;
import prod.oldboy.validation.group.UpdateAction;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/emails")
@RequiredArgsConstructor
@Tag(name = "Email manipulation", description = "Methods for update/delete/create email (the user must log in)")
public class EmailController {

    private final EmailService emailService;

    @DeleteMapping("/{email}/delete")
    @Operation(summary = "Delete email of authenticated user",
               description = "Enter standard email format")
    public ResponseEntity<?> delete(@PathVariable("email") String email) {

        log.info("EmailController: delete method is start");

        ResponseEntity<?> answer = emailService.delete(email)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();

        log.info("EmailController: delete method is finished");

        return answer;
    }

    @PutMapping("/update")
    @Operation(summary = "Update email of authenticated user",
               description = "Enter old email of standard format and enter new email")
    public ResponseEntity<?> update(@Validated(UpdateAction.class)
                                    @RequestBody
                                    EmailUpdateDto emailUpdateDto) {

        log.info("EmailController: update method is start");

        ResponseEntity<?> answer = emailService.update(emailUpdateDto).
                map(content -> ResponseEntity.ok().body(content.email()))
                .orElseGet(() -> ResponseEntity.notFound().build());

        log.info("EmailController: update method is finished");

        return answer;
    }

    @PostMapping("/create")
    @Operation(summary = "Create new email of authenticated user",
               description = "Enter email of standard format")
    public ResponseEntity<?> create(@Validated(CreateAction.class)
                                    @RequestBody
                                    EmailCreateDto emailCreateDto) {

        log.info("EmailController: create method is start");

        ResponseEntity<?> answer = emailService.create(emailCreateDto).
                map(content -> ResponseEntity.ok().body(content.email()))
                .orElseGet(() -> ResponseEntity.notFound().build());

        log.info("EmailController: create method is finished");

        return answer;
    }
}
