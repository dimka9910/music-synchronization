package com.github.music.synchronization.service.service;


import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.YandexDto;

public interface AuthService {

    AuthResponseDto getAuthUrl(AuthRequestDto token);

    BaseDataResponse saveToken(AuthCodeDto authCodeDto);

    YandexDto registerYandex(YandexDto yandexDto);

}
