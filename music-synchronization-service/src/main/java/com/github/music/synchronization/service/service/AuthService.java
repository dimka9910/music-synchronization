package com.github.music.synchronization.service.service;


import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.TokenDto;

import java.util.List;

public interface AuthService {

    BaseDataResponse<AuthResponseDto> getAuthUrl(AuthRequestDto token);

    BaseDataResponse<TokenDto> saveToken(TokenDto tokenDto);
}
