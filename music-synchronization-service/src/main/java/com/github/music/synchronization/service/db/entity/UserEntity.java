package com.github.music.synchronization.service.db.entity;


import com.github.music.synchronization.dto.enums.MusicProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.Provider;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "musicusers")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "tg_bot_id")
    String tgBotId;

    @Column(name = "yandex_login")
    String yandexLogin;

    @Column(name = "yandex_id")
    String yandexId;

    @Column(name = "spotify_id")
    String spotifyId;

    @Column(name = "apple_id")
    String appleId;

    public void setServiceId(String guid, MusicProvider musicProvider){
        switch (musicProvider){
            case APPLE:
                this.appleId = guid;
                break;
            case SPOTIFY:
                this.spotifyId = guid;
                break;
            case YANDEX:
                this.yandexId = guid;
                break;
        }
    }

    public String getServiceId(MusicProvider musicProvider){
        switch (musicProvider){
            case APPLE:
                return appleId;
            case SPOTIFY:
                return spotifyId;
            case YANDEX:
                return yandexId;
            default:
                return null;
        }
    }

}
