package com.github.music.synchronization.service.tools;

import com.github.music.synchronization.dto.enums.MusicProvider;
import com.github.music.synchronization.dto.enums.MusicServiceActions;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "music-urls")
public class MusicUrlHelper {

    private Map<MusicProvider, String> urlMap;
    private Map<MusicServiceActions, String> actionsMap;


}
