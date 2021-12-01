package com.github.music.synchronization.service.service.impl;


import com.github.music.synchronization.dto.token.AccountDto;
import com.github.music.synchronization.dto.token.AccountTokenDto;
import com.github.music.synchronization.dto.token.AddTokensToAccountDto;
import com.github.music.synchronization.service.service.TokenService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenServiceImpl implements TokenService {

    @Override
    public AccountDto getAccountByToken(String token) {
        return null;
    }

    @Override
    public List<AccountTokenDto> getTokensByAccount(String login) {
        return null;
    }

    @Override
    public List<AccountTokenDto> getLastTokensByAccount(String login) {
        return null;
    }

    @Override
    public AccountDto saveNewAccount(AccountDto accountDto) {
        return null;
    }

    @Override
    public List<AccountTokenDto> saveAllTokens(AddTokensToAccountDto addTokensToAccountDto) {
        return null;
    }

    @Override
    public void deleteTokensByAccount(String login) {

    }

    @Override
    public void deleteTokensOfCurrentAccount() {

    }

    @Override
    public void deleteAccount(String login) {

    }
}
