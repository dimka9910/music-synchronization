package com.github.music.synchronization.service.service.impl;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.music.TrackDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.mockito.Mockito.*;

public class MusicServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ServiceClient serviceClient;
    @Mock
    Logger log;
    @InjectMocks
    MusicServiceImpl musicServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransferPlaylistViaTGBot() throws Exception {
        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").yandexId("yandexId").build()));
        when(userRepository.getFirstByYandexLogin(anyString())).thenReturn(null);

        when(serviceClient.exportPlaylist(any())).thenReturn(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"));
        when(serviceClient.importPlaylist(any(), any())).thenReturn(new YandexImportStatus("status", "message"));

        YandexImportStatus result = musicServiceImpl.transferPlaylist(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, "tgBotId", "guid", null));
        Assert.assertEquals(new YandexImportStatus("status", "message"), result);
    }

    @Test
    public void testTransferPlaylistViaAlisa() throws Exception {
        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(null);
        when(userRepository.getFirstByYandexLogin(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().yandexId("yandexId").spotifyId("testSpotifyId").build()));

        when(serviceClient.exportPlaylist(any())).thenReturn(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"));
        when(serviceClient.importPlaylist(any(), any())).thenReturn(new YandexImportStatus("status", "message"));

        YandexImportStatus result = musicServiceImpl.transferPlaylist(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, null, "guid", "yandexId"));
        Assert.assertEquals(new YandexImportStatus("status", "message"), result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testTransferPlaylistExceptionNoTgBotInfo() throws Exception {
        when(userRepository.getFirstByTgBotId(eq("tgBotId"))).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));

        when(serviceClient.exportPlaylist(any())).thenReturn(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"));
        when(serviceClient.importPlaylist(any(), any())).thenReturn(new YandexImportStatus("status", "message"));

        YandexImportStatus result = musicServiceImpl.transferPlaylist(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, null, "guid", null));
        Assert.assertEquals(new YandexImportStatus("status", "message"), result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testTransferPlaylistExceptionNoServiceIdForMusicProvider() throws Exception {
        when(userRepository.getFirstByYandexLogin(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId(null).build()));

        when(serviceClient.exportPlaylist(any())).thenReturn(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"));
        when(serviceClient.importPlaylist(any(), any())).thenReturn(new YandexImportStatus("status", "message"));

        YandexImportStatus result = musicServiceImpl.transferPlaylist(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, null, "guid", "yandexId"));
        Assert.assertEquals(new YandexImportStatus("status", "message"), result);
    }



    @Test
    public void testGetPlaylistsViaTGBot() throws Exception {

        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));
        when(userRepository.getFirstByYandexLogin(anyString())).thenReturn(null);

        when(serviceClient.getPlaylists(any())).thenReturn(Arrays.<String>asList("String"));

        List<String> result = musicServiceImpl.getPlaylists(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, "tgBotId", "guid", null));
        Assert.assertEquals(Arrays.<String>asList("String"), result);
    }

    @Test
    public void testGetPlaylistsViaAlisa() throws Exception {
        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(null);
        when(userRepository.getFirstByYandexLogin(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));

        when(serviceClient.getPlaylists(any())).thenReturn(Arrays.<String>asList("String"));

        List<String> result = musicServiceImpl.getPlaylists(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, null, "guid", "yandexId"));
        Assert.assertEquals(Arrays.<String>asList("String"), result);
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetPlaylistsExceptionNoTgBotInfo() throws Exception {

        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));
        when(userRepository.getFirstByYandexLogin(anyString())).thenReturn(null);


        when(userRepository.getFirstByTgBotId(eq("tgBotId"))).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));

        when(serviceClient.exportPlaylist(any())).thenReturn(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"));
        when(serviceClient.importPlaylist(any(), any())).thenReturn(new YandexImportStatus("status", "message"));

        List<String> result = musicServiceImpl.getPlaylists(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, null, "guid", null));
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetPlaylistsExceptionNoServiceIdForMusicProvider() throws Exception {
        when(userRepository.getFirstByYandexLogin(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId(null).build()));
        when(userRepository.getFirstByTgBotId(anyString())).thenReturn(Optional.ofNullable(UserEntity.builder().spotifyId("testSpotifyId").build()));


        when(serviceClient.exportPlaylist(any())).thenReturn(new PlaylistDto(Collections.singletonList(TrackDto.builder().name("songname").artist("artist").album("album").build()), "name", "yandexId", "guid"));
        when(serviceClient.importPlaylist(any(), any())).thenReturn(new YandexImportStatus("status", "message"));

        List<String> result = musicServiceImpl.getPlaylists(new PlaylistRequestDto("name", MusicProvider.SPOTIFY, null, "guid", "yandexId"));
    }
}

