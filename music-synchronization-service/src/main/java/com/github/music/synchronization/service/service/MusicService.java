package com.github.music.synchronization.service.service;

import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.PagingResponseDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MusicService {

    YandexImportStatus transferPlaylist(PlaylistRequestDto playlistRequestDto);

    List<String> getPlaylists(PlaylistRequestDto playlistRequestDto);

}
