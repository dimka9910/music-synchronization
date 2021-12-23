package com.github.music.synchronization.dto.music;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PlaylistDto", description = "плейлиста")
public class PlaylistDto {
    List<TrackDto> tracks;
    String name;
    String yandexId;
    String guid;
}
