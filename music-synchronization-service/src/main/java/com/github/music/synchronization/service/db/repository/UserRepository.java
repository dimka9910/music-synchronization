package com.github.music.synchronization.service.db.repository;

import com.github.music.synchronization.service.db.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> getFirstByTgBotId(String tgBotId);
    Optional<UserEntity> getFirstByYandexLogin(String yandexLogin);
}
