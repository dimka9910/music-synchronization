package com.github.music.synchronization.rest;

import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.AuthResponseDto;
import com.github.music.synchronization.dto.token.TokenDto;
import com.github.music.synchronization.service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public BaseDataResponse<AuthResponseDto> getAuthUrl(@RequestBody AuthRequestDto authRequestDto) {
        return authService.getAuthUrl(authRequestDto);
    }

    @Operation(description = "Получение ссылки на авторизацию")
    @PostMapping(value = "/save-user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<TokenDto> saveUser(@RequestBody TokenDto tokenDto) {
        return authService.saveToken(tokenDto);
    }

    @Operation(description = "Получение ссылки на авторизацию")
    @PostMapping(value = "/save-user2", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<TokenDto> saveUser2(@RequestBody TokenDto tokenDto) {
        return authService.saveToken2(tokenDto);
    }

}
