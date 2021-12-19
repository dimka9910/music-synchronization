package com.github.music.synchronization.service.resttemplate;


import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.music.PlaylistDto;
import com.github.music.synchronization.dto.request.PlaylistRequestDto;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.YandexDto;

public interface ServiceClient {


    AuthResponseDto getAuthUrl(AuthRequestDto authRequestDto);

    AuthCodeDto getGuidByAuthCode(AuthCodeDto authCodeDto);

    YandexDto registerYandex(YandexDto yandexDto);

    PlaylistDto getPlaylist(PlaylistRequestDto playlistRequestDto);

    PlaylistDto importPlaylist(PlaylistDto playlistDto, MusicProvider musicProvider);
}
