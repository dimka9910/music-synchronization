package com.github.music.synchronization.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AddTokensToAccountDto", description = "Дто для добавляния или обновления пользователя и его токенов")
public class AddTokensToAccountDto {

    @Deprecated
    @Schema(name = "tokens", description = "Account's tokens", nullable = false)
    List<String> tokens;

    @Schema(name = "expiredIn", description = "дата до которой действуют токены", nullable = false)
    private Date expiredIn;
}
