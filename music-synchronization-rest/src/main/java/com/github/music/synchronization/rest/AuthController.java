package com.github.music.synchronization.rest;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.AuthCodeDto;
import com.github.music.synchronization.dto.token.YandexDto;
import com.github.music.synchronization.service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Tag(description = "Контроллер для авторизации", name = "AuthController")
@Slf4j
@RestController
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(description = "Получение ссылки на авторизацию")
    @PostMapping(value = "/get-url", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponseDto getAuthUrl(@RequestBody AuthRequestDto authRequestDto) {
        log.info("Запрос на получение ссылки: " + authRequestDto);
        return authService.getAuthUrl(authRequestDto);
    }

    @Operation(description = "Регистрация в яндекс")
    @PostMapping(value = "/yandex-auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public YandexDto getAuthUrl(@RequestBody YandexDto yandexDto) {
        log.info("Запрос на регистрацию яндекса: " + yandexDto);
        return authService.registerYandex(yandexDto);
    }

    @Operation(description = "редирект ссылка")
    @GetMapping(value = "/save-code/{provider}", produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public String saveUser(@PathVariable("provider") MusicProvider provider,
                                                  @RequestParam("code") String code,
                                                  @RequestParam("state") String state) {
        log.info("вызов редиректа: " + provider + " " + code);
        AuthCodeDto authCodeDto = AuthCodeDto.builder().musicProvider(provider).tgBotId(state).authCode(code).build();
        authService.saveToken(authCodeDto);

        return "<h1>You are successfully authorized to " + provider.name().toLowerCase() + "!</h1>\n" +
                "<p><img style=\"float: left;\" src=\"https://i.postimg.cc/8Cq2Sk6M/sticker-vk-mikewazowski-004-removebg-preview.webp\" width=\"250\" height=\"250\" /></p>";

    }
}
