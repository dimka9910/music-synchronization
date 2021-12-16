package com.github.music.synchronization.service.resttemplate;


import com.github.music.synchronization.dto.enums.MusicServiceActions;
import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.TokenDto;
import com.github.music.synchronization.service.tools.MusicUrlHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.BitSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceClientImpl implements ServiceClient {

    @Value("${services.music-service.url}")
    private String musicServiceUrl;

    public String getUriPath() {
        return musicServiceUrl;
    }

    private final MusicUrlHelper musicUrlHelper;
    private final RestTemplate appRestClient;


    @Override
    public String getAuthUrl(AuthRequestDto authRequestDto) {
        return appRestClient.exchange(musicUrlHelper.getUrlMap().get(authRequestDto.getMusicProvider())
                        + musicUrlHelper.getActionsMap().get(MusicServiceActions.AUTH), HttpMethod.GET,
                new HttpEntity<>(null, null), new ParameterizedTypeReference<String>() {
                }).getBody();
    }

    @Override
    public BaseDataResponse<TokenDto> saveTokenTest(TokenDto tokenDto) {
        return appRestClient.exchange(getUriPath() + "/rest/auth/save-user", HttpMethod.POST, new HttpEntity<>(tokenDto, null),
                new ParameterizedTypeReference<BaseDataResponse<TokenDto>>() {
                }).getBody();
    }

}
