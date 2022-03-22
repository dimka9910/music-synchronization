package com.github.music.synchronization.service.service;

import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.PagingResponseDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
import com.github.music.synchronization.service.db.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MusicService {

    UserEntity getUserEntityFromPlaylistRequest(PlaylistRequestDto playlistRequestDto);

    YandexImportStatus transferPlaylist(PlaylistRequestDto playlistRequestDto);

    List<String> getPlaylists(PlaylistRequestDto playlistRequestDto);

}
