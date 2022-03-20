package com.github.music.synchronization.service.resttemplate;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.enums.MusicServiceActions;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.music.TrackDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.service.tools.MusicUrlHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.Mockito.*;

public class ServiceClientImplTest {
    @Mock
    MusicUrlHelper musicUrlHelper;
    @Mock
    RestTemplate appRestClient;
    @Mock
    Logger log;
    @InjectMocks
    ServiceClientImpl serviceClientImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAuthUrl() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.APPLE, "https://6f97-176-59-37-144.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.URL, "/authUrl");
        }});
        String actualUrl = "https://6f97-176-59-37-144.ngrok.io/authUrl";

        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<AuthResponseDto>>any()))
                .thenReturn(new ResponseEntity<>(AuthResponseDto.builder().url("url").build(), HttpStatus.OK));

        AuthResponseDto result = serviceClientImpl.getAuthUrl(new AuthRequestDto(MusicProvider.APPLE, "tgBotId", "guid"));
        Assert.assertEquals(new AuthResponseDto("url"), result);
    }

    @Test
    public void testGetGuidByAuthCode() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.SPOTIFY, "https://6f97-176-59-37-144.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.CODE, "/register");
        }});
        String actualUrl = "https://6f97-176-59-37-144.ngrok.io/register";

        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<AuthCodeDto>>any()))
                .thenReturn(new ResponseEntity<>(AuthCodeDto.builder().guid("guid").musicProvider(MusicProvider.SPOTIFY).build(), HttpStatus.OK));

        AuthCodeDto result = serviceClientImpl.getGuidByAuthCode(new AuthCodeDto(null, null, "authCode", MusicProvider.SPOTIFY));
        Assert.assertEquals(new AuthCodeDto("guid", null, null, MusicProvider.SPOTIFY), result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetGuidByAuthCodeBadRequest() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.SPOTIFY, "https://6f97-176-59-37-144.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.CODE, "/register");
        }});
        String actualUrl = "https://6f97-176-59-37-144.ngrok.io/register";

        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<AuthCodeDto>>any()))
                .thenReturn(new ResponseEntity<>(AuthCodeDto.builder().guid("guid").musicProvider(MusicProvider.SPOTIFY).build(), HttpStatus.BAD_REQUEST));

        serviceClientImpl.getGuidByAuthCode(new AuthCodeDto(null, null, "authCode", MusicProvider.SPOTIFY));
    }

    @Test
    public void testRegisterYandex() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.YANDEX, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.REGISTER_YANDEX, "/register");
        }});

        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/register";


        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<YandexDto>>any()))
                .thenReturn(new ResponseEntity<>(YandexDto.builder().yandexId("yandexId").tgBotId("tgBotId").build(), HttpStatus.OK));

        YandexDto result = serviceClientImpl.registerYandex(new YandexDto("username", "password", "tgBotId", "yandexId"));
        Assert.assertEquals(YandexDto.builder().yandexId("yandexId").tgBotId("tgBotId").build(), result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testRegisterYandexException() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.YANDEX, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.REGISTER_YANDEX, "/register");
        }});

        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/register";


        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<YandexDto>>any()))
                .thenReturn(new ResponseEntity<>(YandexDto.builder().yandexId("yandexId").tgBotId("tgBotId").build(), HttpStatus.BAD_REQUEST));

        YandexDto result = serviceClientImpl.registerYandex(new YandexDto("username", "password", "tgBotId", "yandexId"));
        Assert.assertEquals(YandexDto.builder().yandexId("yandexId").tgBotId("tgBotId").build(), result);
    }

    @Test
    public void testExportPlaylist() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.SPOTIFY, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.EXPORT_PLAYLIST, "/playlist");
        }});

        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/playlist";

        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<PlaylistDto>>any()))
                .thenReturn(new ResponseEntity<>(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"), HttpStatus.OK));

        PlaylistDto result = serviceClientImpl.exportPlaylist(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, "tgBotId", "guid", "yandexId"));
        Assert.assertEquals(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"), result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testExportPlaylistExceptionUnauthorized() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.SPOTIFY, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.EXPORT_PLAYLIST, "/playlist");
        }});

        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/playlist";

        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<PlaylistDto>>any()))
                .thenReturn(new ResponseEntity<>(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"), HttpStatus.UNAUTHORIZED));

        PlaylistDto result = serviceClientImpl.exportPlaylist(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, "tgBotId", "guid", "yandexId"));
    }

    @Test(expected = ResponseStatusException.class)
    public void testExportPlaylistExceptionNotFound() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.SPOTIFY, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.EXPORT_PLAYLIST, "/playlist");
        }});

        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/playlist";

        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<PlaylistDto>>any()))
                .thenReturn(new ResponseEntity<>(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"), HttpStatus.NOT_FOUND));

        PlaylistDto result = serviceClientImpl.exportPlaylist(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, "tgBotId", "guid", "yandexId"));
    }

    @Test
    public void testGetPlaylists() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.SPOTIFY, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.SEARCH_PLAYLIST, "/playlistsList");
        }});
        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/playlistsList";


        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<List<String>>>any()))
                .thenReturn(new ResponseEntity<>(Arrays.asList("pl1","pl2"), HttpStatus.OK));


        List<String> result = serviceClientImpl.getPlaylists(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, "tgBotId", "guid", "yandexId"));
        Assert.assertEquals(Arrays.<String>asList("pl1","pl2"), result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetPlaylistsException() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.SPOTIFY, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.SEARCH_PLAYLIST, "/playlistsList");
        }});
        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/playlistsList";


        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<List<String>>>any()))
                .thenReturn(new ResponseEntity<>(Arrays.asList("pl1","pl2"), HttpStatus.UNAUTHORIZED));


        List<String> result = serviceClientImpl.getPlaylists(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, "tgBotId", "guid", "yandexId"));
    }

    @Test
    public void testImportPlaylist() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.YANDEX, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.IMPORT_PLAYLIST, "/synchronize_tracks");
        }});

        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/synchronize_tracks";

        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<YandexImportStatus>>any()))
                .thenReturn(new ResponseEntity<>(new YandexImportStatus("status", "message"), HttpStatus.OK));

        YandexImportStatus result = serviceClientImpl.importPlaylist(new PlaylistDto(Arrays.<TrackDto>asList(new TrackDto("name", "album", "artist")), "name", "yandexId", "guid"), MusicProvider.YANDEX);
        Assert.assertEquals(new YandexImportStatus("status", "message"), result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testImportPlaylistException() throws Exception {
        when(musicUrlHelper.getUrlMap()).thenReturn(new HashMap<MusicProvider, String>() {{
            put(MusicProvider.YANDEX, "https://4b7f-31-134-188-214.ngrok.io");
        }});
        when(musicUrlHelper.getActionsMap()).thenReturn(new HashMap<MusicServiceActions, String>() {{
            put(MusicServiceActions.IMPORT_PLAYLIST, "/synchronize_tracks");
        }});

        String actualUrl = "https://4b7f-31-134-188-214.ngrok.io/synchronize_tracks";

        when(appRestClient.exchange(eq(actualUrl), any(HttpMethod.class), any(), ArgumentMatchers.<ParameterizedTypeReference<YandexImportStatus>>any()))
                .thenReturn(new ResponseEntity<>(new YandexImportStatus("status", "message"), HttpStatus.UNAUTHORIZED));

        YandexImportStatus result = serviceClientImpl.importPlaylist(new PlaylistDto(Arrays.<TrackDto>asList(new TrackDto("name", "album", "artist")), "name", "yandexId", "guid"), MusicProvider.YANDEX);
    }
}
