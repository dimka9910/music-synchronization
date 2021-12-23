package com.github.music.synchronization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseDataResponse<T> {

    @Schema(
            description = "Данные"
    )
    protected T data;
    @Schema(
            description = "Успешное ли выполнение"
    )
    protected Boolean success = true;
    @Schema(
            description = "Список ошибок"
    )
    protected List<String> errors;
}
