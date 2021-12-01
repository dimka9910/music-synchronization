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
@Schema(name = "AlbumDto", description = "Альбом")
public class AlbumDto {
    @Schema(name = "name")
    private String name;
}
