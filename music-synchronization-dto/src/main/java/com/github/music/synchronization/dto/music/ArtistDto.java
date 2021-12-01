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
@Schema(name = "ArtistDto", description = "Исполнитель")
public class ArtistDto {
    @Schema(name = "name")
    private String name;

}
