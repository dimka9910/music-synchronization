package com.github.music.synchronization.rest;


import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.PagingResponseDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
import com.github.music.synchronization.service.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(description = "Контроллер для поиска плейлистов ", name = "PlaylistController")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/playlists")
public class PlaylistController {


    private final MusicService musicService;

    @Operation(description = "Трансфер плейлистов")
    @PostMapping(value = "/transfer-playlist", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public YandexImportStatus transferPlaylist(@RequestBody PlaylistRequestDto playlistRequestDto) {
        log.info("запрос на трансфер плейлистов: " + playlistRequestDto);
        return musicService.transferPlaylist(playlistRequestDto);
    }

    @Operation(description = "Поиск плейлистов")
    @PostMapping(value = "/playlists", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getPlaylists(@RequestBody PlaylistRequestDto playlistRequestDto) {
        log.info("запрос на трансфер плейлистов: " + playlistRequestDto);
        return musicService.getPlaylists(playlistRequestDto);
    }


}
