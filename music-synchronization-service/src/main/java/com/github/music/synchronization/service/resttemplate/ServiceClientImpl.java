package com.github.music.synchronization.service.resttemplate;


import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.enums.MusicServiceActions;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.service.tools.MusicUrlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ServiceClientImpl implements ServiceClient {

    private final MusicUrlHelper musicUrlHelper;
    private final RestTemplate appRestClient;


    @Override
    public AuthResponseDto getAuthUrl(AuthRequestDto authRequestDto) {
        return appRestClient.exchange(musicUrlHelper.getUrlMap().get(authRequestDto.getMusicProvider())
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.URL), HttpMethod.POST,
                new HttpEntity<>(authRequestDto, null), new ParameterizedTypeReference<AuthResponseDto>() {
                }).getBody();
    }


    /**
     * Запрос на получение guid пользователя из музыкального сервиса после регистрации
     **/
    @Override
    public AuthCodeDto getGuidByAuthCode(AuthCodeDto authCodeDto) {
        ResponseEntity<AuthCodeDto> responseEntity = appRestClient.exchange(musicUrlHelper.getUrlMap().get(authCodeDto.getMusicProvider())
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.CODE), HttpMethod.POST,
                new HttpEntity<>(authCodeDto, null), new ParameterizedTypeReference<AuthCodeDto>() {
                });

        log.info(String.valueOf(responseEntity.getBody()));

        switch (responseEntity.getStatusCode()) {
            case BAD_REQUEST:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }

        return responseEntity.getBody();
    }


    @Override
    public YandexDto registerYandex(YandexDto yandexDto) {
        ResponseEntity<YandexDto> responseEntity = appRestClient.exchange(musicUrlHelper.getUrlMap().get(MusicProvider.YANDEX)
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.REGISTER_YANDEX), HttpMethod.POST,
                new HttpEntity<>(yandexDto, null), new ParameterizedTypeReference<YandexDto>() {
                });

        switch (responseEntity.getStatusCode()) {
            case BAD_REQUEST:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the login or password is incorrect");
        }
        return responseEntity.getBody();
    }

    @Override
    public PlaylistDto exportPlaylist(PlaylistRequestDto playlistRequestDto) {
        ResponseEntity<PlaylistDto> responseEntity = appRestClient.exchange(musicUrlHelper.getUrlMap().get(playlistRequestDto.getMusicProvider())
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.EXPORT_PLAYLIST), HttpMethod.POST,
                new HttpEntity<>(playlistRequestDto, null), new ParameterizedTypeReference<PlaylistDto>() {
                });

        switch (responseEntity.getStatusCode()) {
            case NOT_FOUND:
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "playlist not found in " + playlistRequestDto.getMusicProvider().name());
            case UNAUTHORIZED:
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, playlistRequestDto.getMusicProvider().name() + " auth failed");
        }
        return responseEntity.getBody();
    }

    @Override
    public List<String> getPlaylists(PlaylistRequestDto playlistRequestDto) {
        ResponseEntity<List<String>> responseEntity = appRestClient.exchange(musicUrlHelper.getUrlMap().get(playlistRequestDto.getMusicProvider())
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.SEARCH_PLAYLIST), HttpMethod.POST,
                new HttpEntity<>(playlistRequestDto, null), new ParameterizedTypeReference<List<String>>() {
                });

        switch (responseEntity.getStatusCode()) {
            case UNAUTHORIZED:
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, playlistRequestDto.getMusicProvider().name() + " auth failed");
        }
        log.info("Полученные плейлисты: " + responseEntity.getBody());
        return responseEntity.getBody();
    }

    @Override
    public YandexImportStatus importPlaylist(PlaylistDto playlistDto, MusicProvider musicProvider) {
        ResponseEntity<YandexImportStatus> responseEntity = appRestClient.exchange(musicUrlHelper.getUrlMap().get(musicProvider)
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.IMPORT_PLAYLIST), HttpMethod.POST,
                new HttpEntity<>(playlistDto, null), new ParameterizedTypeReference<YandexImportStatus>() {});

        switch (responseEntity.getStatusCode()) {
            case UNAUTHORIZED:
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, musicProvider.name() + " auth failed");
        }
        return responseEntity.getBody();
    }
}
