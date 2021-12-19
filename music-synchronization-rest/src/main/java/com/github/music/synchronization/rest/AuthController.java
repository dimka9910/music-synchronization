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
        return authService.getAuthUrl(authRequestDto);
    }

    @Operation(description = "Регистрация в яндекс")
    @PostMapping(value = "/yandex-auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public YandexDto getAuthUrl(@RequestBody YandexDto yandexDto) {
        return authService.registerYandex(yandexDto);
    }

    @Operation(description = "редирект ссылка")
    @PostMapping(value = "/save-code/{provider}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public BaseDataResponse<AuthCodeDto> saveUser(@PathVariable("provider") MusicProvider provider,
                                                  @RequestParam("code") String code) {
        AuthCodeDto authCodeDto = AuthCodeDto.builder().musicProvider(provider).authCode(code).build();
        return authService.saveToken(authCodeDto);
    }


//    change your return type to ResponseEntity<>, then you can use below for 400
//
//return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//and for correct request
//
//return new ResponseEntity<>(json,HttpStatus.OK);
//UPDATE 1
//
//after spring 4.1 there are helper methods in ResponseEntity could be used as
//
//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//and
//
//return ResponseEntity.ok(json);

}
