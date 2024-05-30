package prod.oldboy.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prod.oldboy.dto.phone_dto.PhoneCreateDto;
import prod.oldboy.dto.phone_dto.PhoneUpdateDto;
import prod.oldboy.service.PhoneService;
import prod.oldboy.validation.group.CreateAction;
import prod.oldboy.validation.group.UpdateAction;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/phones")
@RequiredArgsConstructor
@Tag(name = "Phone manipulation", description = "Methods for update/delete/create phones (the user must log in)")
public class PhoneController {

    private final PhoneService phoneService;

    @DeleteMapping("/{phone}/delete")
    @Operation(summary = "Delete phone of authenticated user",
               description = "Enter phone number (format: min = 6, max = 12 digits)")
    public ResponseEntity<?> delete(@PathVariable("phone") String phone) {

        log.info("PhoneController: delete method is start");

        ResponseEntity<?> answer = phoneService.delete(phone)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();

        log.info("PhoneController: delete method is finished");

        return answer;
    }

    @PutMapping("/update")
    @Operation(summary = "Update phone of authenticated user",
            description = "Enter old phone of standard format and enter new phone (format: min = 6, max = 12 digits)")
    public ResponseEntity<?> update(@Validated(UpdateAction.class)
                                    @RequestBody
                                    PhoneUpdateDto phoneUpdateDto) {

        log.info("PhoneController: update method is start");

        ResponseEntity<?> answer = phoneService.update(phoneUpdateDto).
                map(content -> ResponseEntity.ok().body(content.phone()))
                .orElseGet(() -> ResponseEntity.notFound().build());

        log.info("PhoneController: update method is finish");

        return answer;
    }

    @PostMapping("/create")
    @Operation(summary = "Create new phone of authenticated user",
            description = "Enter phone of standard format")
    public ResponseEntity<?> create(@Validated(CreateAction.class)
                                    @RequestBody
                                    PhoneCreateDto phoneCreateDto) {

        log.info("PhoneController: create method is start");

        ResponseEntity<?> answer = phoneService.create(phoneCreateDto).
                map(content -> ResponseEntity.ok().body(content.phone()))
                .orElseGet(() -> ResponseEntity.notFound().build());

        log.info("PhoneController: create method is finished");

        return answer;
    }
}
