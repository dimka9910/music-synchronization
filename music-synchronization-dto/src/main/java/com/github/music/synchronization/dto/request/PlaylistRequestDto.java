package com.github.music.synchronization.dto.request;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.token.TokenDto;
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
    @Schema(name = "playlistDto")
    PlaylistDto playlistDto;
    @Schema(name = "musicProvider")
    MusicProvider musicProvider;
    @Schema(name = "tokenDto")
    TokenDto tokenDto;
}
