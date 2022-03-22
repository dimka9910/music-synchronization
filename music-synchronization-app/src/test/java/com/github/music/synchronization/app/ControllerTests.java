package com.github.music.synchronization.app;

import com.github.music.synchronization.app.config.ApplicationConfiguration;
import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.music.TrackDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.rest.AuthController;
import com.github.music.synchronization.service.db.entity.UserEntity;
import com.github.music.synchronization.service.db.repository.UserRepository;
import com.github.music.synchronization.service.resttemplate.ServiceClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AuthController authController;
    @Autowired
    UserRepository userRepository;
    @MockBean
    ServiceClient serviceClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Transactional
    public void getAuthUrlTest() throws Exception {
        when(serviceClient.getAuthUrl(any(AuthRequestDto.class))).thenReturn(AuthResponseDto.builder().url(url).build());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/auth/get-url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthRequestDto.builder().guid(spotifyGuid).musicProvider(provider).tgBotId(tgBotId).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.url").value(url));
    }

    @Test
    @Transactional
    public void registerYandexTest() throws Exception {
        when(serviceClient.registerYandex(any(YandexDto.class))).thenReturn(YandexDto.builder().yandexId(yandexGuid).build());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/auth/yandex-auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(YandexDto.builder().tgBotId(tgBotId).username(yandexLogin).password("any").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.yandexId").value(yandexGuid));
    }

    @Test
    @Transactional
    public void getRedirectResultTest() throws Exception {
        when(serviceClient.getGuidByAuthCode(any(AuthCodeDto.class))).thenReturn(AuthCodeDto.builder().guid(spotifyGuid).build());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rest/auth/save-code/" + provider.toString())
                        .contentType(MediaType.ALL_VALUE).param("code", authCode).param("state", tgBotId))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void transferPlaylistTest() throws Exception {
        UserEntity userEntity = UserEntity.builder().spotifyId(spotifyGuid).yandexId(yandexGuid).yandexLogin(yandexLogin).tgBotId(tgBotId).build();
        userRepository.save(userEntity);

        when(serviceClient.exportPlaylist(any(PlaylistRequestDto.class))).thenReturn(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "rock", null, null));
        when(serviceClient.importPlaylist(any(PlaylistDto.class), any(MusicProvider.class))).thenReturn(YandexImportStatus.builder().status("success").message("ok").build());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/playlists/transfer-playlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlaylistRequestDto.builder().tgBotId(tgBotId).musicProvider(MusicProvider.SPOTIFY).name("rock").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("ok"));
    }

    @Test
    @Transactional
    public void getPlaylistsTest() throws Exception {
        UserEntity userEntity = UserEntity.builder().spotifyId(spotifyGuid).yandexId(yandexGuid).yandexLogin(yandexLogin).tgBotId(tgBotId).build();
        userRepository.save(userEntity);

        when(serviceClient.getPlaylists(any(PlaylistRequestDto.class))).thenReturn(Arrays.asList("rock","pop","educational"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/playlists/playlists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlaylistRequestDto.builder().tgBotId(tgBotId).musicProvider(MusicProvider.SPOTIFY).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0]").value("rock"))
                .andExpect(jsonPath("$[1]").value("pop"))
                .andExpect(jsonPath("$[2]").value("educational"));
    }




    public final String tgBotId = "tgBotIdTest";
    public final MusicProvider provider = MusicProvider.SPOTIFY;
    public final String spotifyGuid = "spotifyGuidTest";
    public final String yandexGuid = "yandexGuid_test";
    public final String yandexLogin = "yandexLogin_test";
    public final String authCode = "testcode";
    public final String url = "testUrl";
    public final String htmlPage = "<h1>You are successfully authorized to \" + provider.name().toLowerCase() + \"!</h1>\\n\" +\n" +
            "                \"<p><img style=\\\"float: left;\\\" src=\\\"https://i.postimg.cc/8Cq2Sk6M/sticker-vk-mikewazowski-004-removebg-preview.webp\\\" width=\\\"250\\\" height=\\\"250\\\" /></p>";


}
