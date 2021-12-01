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
@Schema(name = "TokenDto", description = "Данные для сохранения токена в базе")
public class TokenDto {
    String guid;
}
