package com.github.music.synchronization.service.service.impl;


import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.TokenDto;
import com.github.music.synchronization.service.db.entity.UserEntity;
import com.github.music.synchronization.service.db.repository.UserRepository;
import com.github.music.synchronization.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AuthService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public BaseDataResponse<AuthResponseDto> getAuthUrl(AuthRequestDto token) {
        return null;
    }

    @Override
    public BaseDataResponse<TokenDto> saveToken(TokenDto tokenDto) {
        userRepository.save(UserEntity.builder().guid(tokenDto.getGuid()).build());
        return userRepository.getFirstByGuid(tokenDto.getGuid())
                .map(v -> TokenDto.builder().guid(v.getGuid()).build())
                .map(v -> BaseDataResponse.<TokenDto>builder().data(v).success(true).build())
                .orElse(BaseDataResponse.<TokenDto>builder().success(false).build());
    }
}
