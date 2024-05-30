package prod.oldboy.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prod.oldboy.database.entity.Amount;
import prod.oldboy.dto.amount_dto.AmountReadDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AmountReadMapper implements Mapper<Optional<Amount>, Optional<AmountReadDto>>{

    @Override
    public Optional<AmountReadDto> map(Optional<Amount> object) {
        return Optional.ofNullable(new AmountReadDto(object.get().getAmount()));
    }
}
