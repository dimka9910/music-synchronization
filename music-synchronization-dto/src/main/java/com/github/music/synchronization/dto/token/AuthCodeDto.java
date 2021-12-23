package com.github.music.synchronization.dto.token;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "TokenDto", description = "Данные для сохранения токена в базе")
public class AuthCodeDto {
    String guid;
    String tgBotId;
    String authCode;
    MusicProvider musicProvider;
}
