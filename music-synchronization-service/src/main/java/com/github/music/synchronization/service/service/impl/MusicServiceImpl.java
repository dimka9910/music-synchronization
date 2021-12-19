package com.github.music.synchronization.service.service.impl;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.PagingResponseDto;
import com.github.music.synchronization.service.db.entity.UserEntity;
import com.github.music.synchronization.service.db.repository.UserRepository;
import com.github.music.synchronization.service.resttemplate.ServiceClient;
import com.github.music.synchronization.service.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final UserRepository userRepository;
    private final ServiceClient serviceClient;


    @Override
    public PlaylistDto transferPlaylist(PlaylistRequestDto playlistRequestDto) {
        UserEntity userEntity = null;
        if (playlistRequestDto.getTgBotId() != null)
            userEntity = userRepository.getFirstByTgBotId(playlistRequestDto.getTgBotId()).orElse(null);
        if (playlistRequestDto.getMusicProvider() != null)
            userEntity = userRepository.getFirstByYandexId(playlistRequestDto.getYandexId()).orElse(null);

        assert playlistRequestDto.getMusicProvider() != null;
        assert userEntity != null;

        playlistRequestDto.setGuid(userEntity.getServiceId(playlistRequestDto.getMusicProvider()));
        PlaylistDto playlistDto = serviceClient.getPlaylist(playlistRequestDto);
        playlistDto.setYandexId(userEntity.getYandexId());
        return serviceClient.importPlaylist(playlistDto, MusicProvider.YANDEX);
    }

}
