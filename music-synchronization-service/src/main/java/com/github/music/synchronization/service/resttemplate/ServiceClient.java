package com.github.music.synchronization.service.resttemplate;


import com.github.music.synchronization.dto.response.BaseDataResponse;
import com.github.music.synchronization.dto.token.AuthRequestDto;
import com.github.music.synchronization.dto.token.TokenDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ServiceClient {


    BaseDataResponse<TokenDto> saveTokenTest(TokenDto notificationDto);


    String getAuthUrl(AuthRequestDto authRequestDto);



}
