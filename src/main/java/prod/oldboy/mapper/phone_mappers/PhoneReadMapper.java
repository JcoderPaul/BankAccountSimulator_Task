package prod.oldboy.mapper.phone_mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prod.oldboy.database.entity.Phone;
import prod.oldboy.dto.phone_dto.PhoneReadDto;
import prod.oldboy.mapper.Mapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PhoneReadMapper implements Mapper<Optional<Phone>, Optional<PhoneReadDto>> {

    @Override
    public Optional<PhoneReadDto> map(Optional<Phone> object) {
        return Optional.ofNullable(new PhoneReadDto(object.get().getUserPhone()));
    }
}
