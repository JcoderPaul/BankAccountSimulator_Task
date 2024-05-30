package prod.oldboy.dto;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PagePaginationResponse<T> {

    List<T> content;

    Metadata metadata;

    public static <T> PagePaginationResponse<T> of(Page<T> page) {
        Metadata metadata = new Metadata(page.getNumber(),
                                         page.getSize(),
                                         page.getTotalElements());
        return new PagePaginationResponse<>(page.getContent(), metadata);
    }

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}