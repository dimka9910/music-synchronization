package com.github.music.synchronization.service.service.impl;


import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.TokenDto;
import com.github.music.synchronization.service.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthServiceImpl implements AuthService {

    @Override
    public BaseDataResponse<AuthResponseDto> getAuthUrl(AuthRequestDto token) {
        return null;
    }

    @Override
    public BaseDataResponse<TokenDto> saveToken(TokenDto tokenDto) {
        return null;
    }
}
