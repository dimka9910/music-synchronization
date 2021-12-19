package com.github.music.synchronization.service.resttemplate;


import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.enums.MusicServiceActions;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.service.tools.MusicUrlHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ServiceClientImpl implements ServiceClient {

    private final MusicUrlHelper musicUrlHelper;
    private final RestTemplate appRestClient;


    @Override
    public AuthResponseDto getAuthUrl(AuthRequestDto authRequestDto) {
        return appRestClient.exchange(musicUrlHelper.getUrlMap().get(authRequestDto.getMusicProvider())
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.URL), HttpMethod.GET,
                new HttpEntity<>(authRequestDto, null), new ParameterizedTypeReference<AuthResponseDto>() {
                }).getBody();
    }


    /**
     * Запрос на получение guid пользователя из музыкального сервиса после регистрации
     **/
    @Override
    public AuthCodeDto getGuidByAuthCode(AuthCodeDto authCodeDto) {
        return appRestClient.exchange(musicUrlHelper.getUrlMap().get(authCodeDto.getMusicProvider())
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.CODE), HttpMethod.GET,
                new HttpEntity<>(authCodeDto, null), new ParameterizedTypeReference<AuthCodeDto>() {
                }).getBody();
    }


    @Override
    public YandexDto registerYandex(YandexDto yandexDto) {
        return appRestClient.exchange(musicUrlHelper.getUrlMap().get(MusicProvider.YANDEX)
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.REGISTER_YANDEX), HttpMethod.POST,
                new HttpEntity<>(yandexDto, null), new ParameterizedTypeReference<YandexDto>() {
                }).getBody();
    }

    @Override
    public PlaylistDto getPlaylist(PlaylistRequestDto playlistRequestDto){
        return appRestClient.exchange(musicUrlHelper.getUrlMap().get(playlistRequestDto.getMusicProvider())
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.EXPORT_PLAYLIST), HttpMethod.POST,
                new HttpEntity<>(playlistRequestDto, null), new ParameterizedTypeReference<PlaylistDto>() {
                }).getBody();
    }

    @Override
    public PlaylistDto importPlaylist(PlaylistDto playlistDto, MusicProvider musicProvider){
        return appRestClient.exchange(musicUrlHelper.getUrlMap().get(musicProvider)
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.IMPORT_PLAYLIST), HttpMethod.POST,
                new HttpEntity<>(playlistDto, null), new ParameterizedTypeReference<PlaylistDto>() {
                }).getBody();
    }
}
