package com.github.music.synchronization.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AccountTokenDto", description = "Пользователь и токен")
public class AccountTokenDto {

    @Schema(name = "token", description = "Account's token", nullable = false)
    private String token;

    @Schema(name = "expiredIn", description = "дата до которой действует токен", nullable = false)
    private Date expiredIn;

}
