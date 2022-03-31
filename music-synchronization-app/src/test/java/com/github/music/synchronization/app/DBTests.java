package com.github.music.synchronization.app;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.rest.AuthController;
import com.github.music.synchronization.service.db.entity.UserEntity;
import com.github.music.synchronization.service.db.repository.UserRepository;
import com.github.music.synchronization.service.resttemplate.ServiceClient;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DBTests {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AuthController authController;
    @Autowired
    UserRepository userRepository;
    @MockBean
    ServiceClient serviceClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    public final String tgBotId = "tgBotIdTest";
    public final MusicProvider provider = MusicProvider.SPOTIFY;
    public final String spotifyGuid = "spotifyGuidTest";
    public final String yandexGuid = "yandexGuid_test";
    public final String yandexLogin = "yandexLogin_test";
    public final String authCode = "testcode";
    public final String url = "testUrl";

    @Test
    @Transactional
    void testRedirectDb() {
        when(serviceClient.getGuidByAuthCode(any(AuthCodeDto.class))).thenReturn(AuthCodeDto.builder().guid(spotifyGuid).build());
        authController.saveUser(MusicProvider.SPOTIFY, "testcode", "tgBotIdTest");

        UserEntity userEntity = userRepository.getFirstByTgBotId(tgBotId).orElse(null);
        Assertions.assertNotNull(userEntity);
        Assertions.assertEquals(tgBotId, userEntity.getTgBotId());
        Assertions.assertEquals(spotifyGuid, userEntity.getServiceId(provider));
    }

    @Test
    @Transactional
    void testRedirectDbExistingUser() {
        UserEntity userEntity = UserEntity.builder().spotifyId(spotifyGuid).tgBotId(tgBotId).build();
        userRepository.save(userEntity);

        userEntity = userRepository.getFirstByTgBotId(tgBotId).orElse(null);
        Assertions.assertNotNull(userEntity);
        Long id = userEntity.getId();

        when(serviceClient.getGuidByAuthCode(any(AuthCodeDto.class))).thenReturn(AuthCodeDto.builder().guid(spotifyGuid + "new").build());
        authController.saveUser(MusicProvider.SPOTIFY, "testcode", "tgBotIdTest");

        userEntity = userRepository.getFirstByTgBotId(tgBotId).orElse(null);
        Assertions.assertNotNull(userEntity);
        Assertions.assertEquals(tgBotId, userEntity.getTgBotId());
        Assertions.assertEquals(id, userEntity.getId());
        Assertions.assertEquals(spotifyGuid + "new", userEntity.getServiceId(provider));
    }

    @Test
    @Transactional
    void testRegisterYandex() {
        UserEntity userEntity = userRepository.getFirstByTgBotId(tgBotId).orElse(null);
        Assertions.assertNull(userEntity);

        when(serviceClient.registerYandex(any(YandexDto.class))).thenReturn(YandexDto.builder().yandexId(yandexGuid).build());

        authController.getAuthUrl(YandexDto.builder().tgBotId(tgBotId).username(yandexLogin).password("any").build());

        userEntity = userRepository.getFirstByYandexLogin(yandexLogin).orElse(null);
        Assertions.assertNotNull(userEntity);
        Assertions.assertEquals(yandexLogin, userEntity.getYandexLogin());
        Assertions.assertEquals(yandexGuid, userEntity.getYandexId());
    }

    @Test
    @Transactional
    void testRegisterYandexExisting() {
        UserEntity userEntity = userRepository.getFirstByTgBotId(tgBotId).orElse(null);
        Assertions.assertNull(userEntity);
        userEntity = UserEntity.builder().spotifyId(spotifyGuid).tgBotId(tgBotId).build();
        userRepository.save(userEntity);
        Long id = userEntity.getId();

        when(serviceClient.registerYandex(any(YandexDto.class))).thenReturn(YandexDto.builder().yandexId(yandexGuid).build());
        authController.getAuthUrl(YandexDto.builder().tgBotId(tgBotId).username(yandexLogin).password("any").build());

        userEntity = userRepository.getFirstByTgBotId(tgBotId).orElse(null);
        Assertions.assertNotNull(userEntity);
        Assertions.assertEquals(tgBotId, userEntity.getTgBotId());
        Assertions.assertEquals(id, userEntity.getId());
        Assertions.assertEquals(yandexLogin, userEntity.getYandexLogin());
        Assertions.assertEquals(yandexGuid, userEntity.getYandexId());
        Assertions.assertEquals(spotifyGuid, userEntity.getServiceId(provider));
    }

    @Test
    @Transactional
    void testRegisterYandexException() {
        UserEntity userEntity = userRepository.getFirstByTgBotId(tgBotId).orElse(null);
        Assertions.assertNull(userEntity);

        when(serviceClient.registerYandex(any(YandexDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "the login or password is incorrect"));
        try {
            authController.getAuthUrl(YandexDto.builder().tgBotId(tgBotId).username(yandexLogin).password("any").build());
        } catch (Exception e) {

        }

        userEntity = userRepository.getFirstByTgBotId(tgBotId).orElse(null);
        Assertions.assertNull(userEntity);
    }

}
