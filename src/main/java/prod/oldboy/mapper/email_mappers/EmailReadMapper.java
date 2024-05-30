package prod.oldboy.mapper.email_mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prod.oldboy.database.entity.Email;
import prod.oldboy.dto.email_dto.EmailReadDto;
import prod.oldboy.mapper.Mapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailReadMapper implements Mapper<Optional<Email>, Optional<EmailReadDto>> {

    @Override
    public Optional<EmailReadDto> map(Optional<Email> object) {
        return Optional.ofNullable(new EmailReadDto(object.get().getUserEmail()));
    }
}
