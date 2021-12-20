package com.github.music.synchronization.service.service;

import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.PagingResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface MusicService {

    PlaylistDto transferPlaylist(PlaylistRequestDto playlistRequestDto);

}
