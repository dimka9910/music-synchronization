package com.github.music.synchronization.service.service.impl;

import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.PagingResponseDto;
import com.github.music.synchronization.service.service.MusicService;
import org.springframework.stereotype.Component;

@Component
public class MusicServiceImpl implements MusicService {


    @Override
    public PagingResponseDto<PlaylistDto> searchPlaylists(PlaylistRequestDto playlistRequestDto) {
        return null;
    }

    @Override
    public PagingResponseDto<PlaylistDto> savePlaylists(PlaylistRequestDto playlistRequestDto) {
        return null;
    }
}
