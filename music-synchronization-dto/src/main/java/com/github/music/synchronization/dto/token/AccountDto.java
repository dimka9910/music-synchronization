package com.github.music.synchronization.dto.token;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AccountDto", description = "Пользователь и токен")
public class AccountDto {

    @Schema(name = "id", description = "Id пользователя в базе данных", nullable = true)
    private Long id;

    @Schema(name = "login", description = "User's login", nullable = false)
    private String login;
}
