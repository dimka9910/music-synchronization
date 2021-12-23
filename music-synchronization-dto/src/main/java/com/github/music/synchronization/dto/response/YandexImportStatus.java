package com.github.music.synchronization.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "YandexImportStatus", description = "Статус импорта плейлиста")
public class YandexImportStatus {
    String status;
    String message;
}
