package com.github.music.synchronization.service.service.impl;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
import com.github.music.synchronization.service.db.entity.UserEntity;
import com.github.music.synchronization.service.db.repository.UserRepository;
import com.github.music.synchronization.service.resttemplate.ServiceClient;
import com.github.music.synchronization.service.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final UserRepository userRepository;
    private final ServiceClient serviceClient;


    @Override
    public YandexImportStatus transferPlaylist(PlaylistRequestDto playlistRequestDto) {
        UserEntity userEntity = null;
        if (playlistRequestDto.getTgBotId() != null && playlistRequestDto.getTgBotId() != null)
            userEntity = userRepository.getFirstByTgBotId(playlistRequestDto.getTgBotId()).orElse(null);
        if (playlistRequestDto.getMusicProvider() != null && playlistRequestDto.getYandexId() != null)
            userEntity = userRepository.getFirstByYandexId(playlistRequestDto.getYandexId()).orElse(null);

        assert playlistRequestDto.getMusicProvider() != null;
        if (userEntity == null){
            log.error("пользователь не найден");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no such user");
        }
        if (userEntity.getServiceId(playlistRequestDto.getMusicProvider()) == null){
            log.error("нет гуида пользователя для музыкального сервиса");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no such user for provider");
        }

        playlistRequestDto.setGuid(userEntity.getServiceId(playlistRequestDto.getMusicProvider()));
        log.info("Запрос из : " + playlistRequestDto.getMusicProvider().name() + " : " + playlistRequestDto);
        PlaylistDto playlistDto = serviceClient.exportPlaylist(playlistRequestDto);
        log.info("Получен плейлист: " + playlistDto);
        playlistDto.setYandexId(userEntity.getYandexId());
        return serviceClient.importPlaylist(playlistDto, MusicProvider.YANDEX);
    }

    @Override
    public List<String> getPlaylists(PlaylistRequestDto playlistRequestDto) {
        UserEntity userEntity = null;
        if (playlistRequestDto.getTgBotId() != null && playlistRequestDto.getTgBotId() != null)
            userEntity = userRepository.getFirstByTgBotId(playlistRequestDto.getTgBotId()).orElse(null);
        if (playlistRequestDto.getMusicProvider() != null && playlistRequestDto.getYandexId() != null)
            userEntity = userRepository.getFirstByYandexId(playlistRequestDto.getYandexId()).orElse(null);

        assert playlistRequestDto.getMusicProvider() != null;
        if (userEntity == null){
            log.error("пользователь не найден");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no such user");
        }
        if (userEntity.getServiceId(playlistRequestDto.getMusicProvider()) == null){
            log.error("нет гуида пользователя для музыкального сервиса");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no such user for provider");
        }
        playlistRequestDto.setGuid(userEntity.getServiceId(playlistRequestDto.getMusicProvider()));

        log.info("Запрос на плейлисты: " + playlistRequestDto);
        return serviceClient.getPlaylists(playlistRequestDto);
    }
}
