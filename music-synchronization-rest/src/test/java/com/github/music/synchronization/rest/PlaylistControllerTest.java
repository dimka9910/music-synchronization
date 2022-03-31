package com.github.music.synchronization.rest;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.response.YandexImportStatus;
import com.github.music.synchronization.service.service.MusicService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class PlaylistControllerTest {
    @Mock
    MusicService musicService;
    @Mock
    Logger log;
    @InjectMocks
    PlaylistController playlistController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransferPlaylist() throws Exception {
        when(musicService.transferPlaylist(any())).thenReturn(new YandexImportStatus("status", "message"));

        YandexImportStatus result = playlistController.transferPlaylist(new PlaylistRequestDto("name", MusicProvider.APPLE, "tgBotId", "guid", "yandexId"));
        Assert.assertEquals(new YandexImportStatus("status", "message"), result);
    }

    @Test
    public void testGetPlaylists() throws Exception {
        when(musicService.getPlaylists(any())).thenReturn(Arrays.<String>asList("String"));

        List<String> result = playlistController.getPlaylists(new PlaylistRequestDto("name", MusicProvider.APPLE, "tgBotId", "guid", "yandexId"));
        Assert.assertEquals(Arrays.<String>asList("String"), result);
    }
}

