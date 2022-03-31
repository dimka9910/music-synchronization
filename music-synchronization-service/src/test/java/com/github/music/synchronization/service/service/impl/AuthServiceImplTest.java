package com.github.music.synchronization.service.service.impl;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.service.db.entity.UserEntity;
import com.github.music.synchronization.service.db.repository.UserRepository;
import com.github.music.synchronization.service.resttemplate.ServiceClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AuthServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ServiceClient serviceClient;
    @Mock
    Logger log;
    @InjectMocks
    AuthServiceImpl authServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAuthUrl() throws Exception {
        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));
        when(serviceClient.getAuthUrl(any())).thenReturn(new AuthResponseDto("url"));

        AuthResponseDto result = authServiceImpl.getAuthUrl(new AuthRequestDto(MusicProvider.SPOTIFY, "tgBotId", "guid"));
        Assert.assertEquals(new AuthResponseDto("url"), result);
    }

    @Test
    public void testSaveToken() throws Exception {
        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));
        when(serviceClient.getGuidByAuthCode(any())).thenReturn(new AuthCodeDto("guid", "tgBotId", "authCode", MusicProvider.APPLE));

        BaseDataResponse result = authServiceImpl.saveToken(new AuthCodeDto("guid", "tgBotId", "authCode", MusicProvider.SPOTIFY));
        Assert.assertEquals(new BaseDataResponse(), result);
    }

    @Test
    public void testRegisterYandex() throws Exception {
        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));
        when(serviceClient.registerYandex(any())).thenReturn(new YandexDto("username", "password", "tgBotId", "yandexId"));

        YandexDto result = authServiceImpl.registerYandex(new YandexDto("username", "password", "tgBotId", "yandexId"));
        Assert.assertEquals(new YandexDto("username", "password", "tgBotId", "yandexId"), result);
    }
}

