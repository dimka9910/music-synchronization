package com.github.music.synchronization.dto.token;

import com.github.music.synchronization.dto.enums.MusicProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AuthRequestDto", description = "Дто на запрос авторизации")
public class AuthRequestDto {
    @Schema(name = "musicProvider", description = "провайдер", nullable = false)
    MusicProvider musicProvider;
}
