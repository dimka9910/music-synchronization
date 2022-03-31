package com.github.music.synchronization.rest;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.service.service.AuthService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Locale;

import static org.mockito.Mockito.*;

public class AuthControllerTest {
    @Mock
    AuthService authService;
    @Mock
    Logger log;
    @InjectMocks
    AuthController authController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAuthUrl() throws Exception {
        when(authService.getAuthUrl(any())).thenReturn(new AuthResponseDto("url"));

        AuthResponseDto result = authController.getAuthUrl(new AuthRequestDto(MusicProvider.APPLE, "tgBotId", "guid"));
        Assert.assertEquals(new AuthResponseDto("url"), result);
    }

    @Test
    public void testGetAuthUrl2() throws Exception {
        when(authService.registerYandex(any())).thenReturn(new YandexDto("username", "password", "tgBotId", "yandexId"));

        YandexDto result = authController.getAuthUrl(new YandexDto("username", "password", "tgBotId", "yandexId"));
        Assert.assertEquals(new YandexDto("username", "password", "tgBotId", "yandexId"), result);
    }

    @Test
    public void testSaveUser() throws Exception {
        when(authService.saveToken(any())).thenReturn(new BaseDataResponse(null, Boolean.TRUE, Arrays.<String>asList("String")));

        String result = authController.saveUser(MusicProvider.APPLE, "code", "state");
        Assert.assertEquals("<h1>You are successfully authorized to " + MusicProvider.APPLE.name().toLowerCase(Locale.ROOT) + "!</h1>\n" +
                "<p><img style=\"float: left;\" src=\"https://i.postimg.cc/8Cq2Sk6M/sticker-vk-mikewazowski-004-removebg-preview.webp\" width=\"250\" height=\"250\" /></p>", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme