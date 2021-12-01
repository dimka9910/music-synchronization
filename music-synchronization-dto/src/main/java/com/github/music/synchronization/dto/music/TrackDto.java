package com.github.music.synchronization.dto.music;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TrackDto", description = "Трек")
public class TrackDto {
    @Schema(name = "name")
    private String name;
    @Schema(name = "album")
    private AlbumDto album;
    @Schema(name = "artistDto")
    private ArtistDto artistDto;

}
