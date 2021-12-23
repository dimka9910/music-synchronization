package com.github.music.synchronization.dto.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "AuthResponseDto", description = "Дто на ответ авторизации")
public class AuthResponseDto {
    @Schema(name = "url", description = "ссылка на авторизацию", nullable = false)
    String url;
}
