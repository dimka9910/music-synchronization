package com.github.music.synchronization.rest;

import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AccountDto;
import com.github.music.synchronization.dto.token.AccountTokenDto;
import com.github.music.synchronization.dto.token.AddTokensToAccountDto;
import com.github.music.synchronization.service.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(description = "Контроллер для получения аккаунта/токена ", name = "AccountTokenRest")
@Slf4j
@RestController
@RequestMapping("/rest/account")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @Operation(description = "Получение токенов текущего пользователя")
    @GetMapping(value = "/tokens", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<List<AccountTokenDto>> getTokensOfUser() {
        return null;

    }

    @Operation(description = "Связка пользователя и токенов")
    @PostMapping(value = "/tokens", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<List<AccountTokenDto>> saveTokensToAccount(@RequestBody AddTokensToAccountDto addTokensToAccountDto) {
        return null;
    }


    @Operation(description = "Получение аккаунта по токену")
    @GetMapping(value = "/get-account/{token}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public BaseDataResponse<AccountDto> getAccountByToken(@Parameter(description = "Токен", required = true) @PathVariable("token") String token) {
        return null;

    }

    @Operation(description = "Получение токенов по логину")
    @GetMapping(value = "/get-tokens/{login}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public BaseDataResponse<List<AccountTokenDto>> getTokensByLogin(@Parameter(description = "Логин пользователя", required = true) @PathVariable("login") String login) {
        return null;

    }

    @Operation(description = "Удаление всех токенов привзанных к аккаунту")
    @DeleteMapping(value = "/delete-tokens/{login}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public BaseDataResponse deleteAllTokensByAccount(@Parameter(description = "Логин пользователя", required = true) @PathVariable("login") String login) {
        return null;

    }

    @Operation(description = "Удаление всех токенов привзанных к текущему аккаунту")
    @DeleteMapping(value = "/delete-tokens", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse deleteAllTokensOfCurrentAccount() {
        return null;

    }

    @Operation(description = "Удаление аккаунта и всех привязанных к нему токенов")
    @DeleteMapping(value = "/delete-account/{login}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public BaseDataResponse deleteAccountWithAllTokens(@Parameter(description = "Логин пользователя", required = true) @PathVariable("login") String login) {
        return null;


    }

    @Operation(description = "Осуществляет logout пользователя")
    @PostMapping(value = "/logout", produces = MediaType.TEXT_HTML_VALUE)
    public String getTokensOfUser(HttpServletRequest request) throws ServletException {
        return null;

    }
}
