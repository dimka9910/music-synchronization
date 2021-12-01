package com.github.music.synchronization.service.service;


import com.github.music.synchronization.dto.token.AccountDto;
import com.github.music.synchronization.dto.token.AccountTokenDto;
import com.github.music.synchronization.dto.token.AddTokensToAccountDto;

import java.util.List;

public interface TokenService {

    AccountDto getAccountByToken(String token);

    List<AccountTokenDto> getTokensByAccount(String login);
    List<AccountTokenDto> getLastTokensByAccount(String login);
    AccountDto saveNewAccount(AccountDto accountDto);

    List<AccountTokenDto> saveAllTokens(AddTokensToAccountDto addTokensToAccountDto);

    void deleteTokensByAccount(String login);

    void deleteTokensOfCurrentAccount();

    void deleteAccount(String login);
}
