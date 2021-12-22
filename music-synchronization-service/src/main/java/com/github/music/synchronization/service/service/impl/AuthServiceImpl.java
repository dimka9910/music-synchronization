package com.github.music.synchronization.service.service.impl;


import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.service.db.entity.UserEntity;
import com.github.music.synchronization.service.db.repository.UserRepository;
import com.github.music.synchronization.service.resttemplate.ServiceClient;
import com.github.music.synchronization.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component("AuthService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ServiceClient serviceClient;

    @Override
    public AuthResponseDto getAuthUrl(AuthRequestDto authRequestDto) {
        String guid = null;
        if (authRequestDto.getTgBotId() != null){
            guid = userRepository.getFirstByTgBotId(authRequestDto.getTgBotId())
                    .map(v -> v.getServiceId(authRequestDto.getMusicProvider()))
                    .orElse(null);
        }
        authRequestDto.setGuid(guid);
        return serviceClient.getAuthUrl(authRequestDto);
    }

    @Override
    @Transactional
    public BaseDataResponse saveToken(AuthCodeDto authCodeDto) {

        String guid = null;
        UserEntity userEntity = null;
        if (authCodeDto.getTgBotId() != null){
            userEntity = userRepository.getFirstByTgBotId(authCodeDto.getTgBotId()).orElse(null);
            if (userEntity != null){
                log.info("Сохранение токена к существующему пользователю: " + userEntity);
                guid = userEntity.getServiceId(authCodeDto.getMusicProvider());
            }
        }
        authCodeDto.setGuid(guid);
        String newGuid = serviceClient.getGuidByAuthCode(authCodeDto).getGuid();
        if (userEntity == null)
            userEntity = new UserEntity();
        if (newGuid != null)
            userEntity.setServiceId(guid, authCodeDto.getMusicProvider());
        userEntity.setTgBotId(authCodeDto.getTgBotId());
        userRepository.save(userEntity);

        return new BaseDataResponse();
    }

    @Override
    public YandexDto registerYandex(YandexDto yandexDto) {
        YandexDto yandexResp = serviceClient.registerYandex(yandexDto);
        UserEntity userEntity = null;
        if (yandexDto.getTgBotId() != null){
            userEntity = userRepository.getFirstByTgBotId(yandexDto.getTgBotId()).orElse(null);
        }
        if (userEntity == null)
            userEntity = new UserEntity();

        log.info("пользователь при регистрации яндекса: " + userEntity);
        userEntity.setServiceId(yandexResp.getYandexId(), MusicProvider.YANDEX);
        return  yandexResp;
    }
}
