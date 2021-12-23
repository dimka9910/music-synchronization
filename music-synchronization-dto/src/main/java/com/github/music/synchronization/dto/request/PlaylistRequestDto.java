package com.github.music.synchronization.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PlaylistRequestDto", description = "Запрос с плейлистои")
public class PlaylistRequestDto {
    @JsonProperty("name")
    @Schema(name = "name")
    String name;

    MusicProvider musicProvider;

    String tgBotId;

    String guid;

    String yandexId;
}
