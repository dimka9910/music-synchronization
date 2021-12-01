package com.github.music.synchronization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponseDto<T> extends BaseDataResponse<Collection<T>> {
    @Schema(
            description = "Номер страницы"
    )
    private Integer page;
    @Schema(
            description = "Всего найдено"
    )
    private long total;
    @Schema(
            description = "Кол-во элементов на странице"
    )
    private Integer limit;
}