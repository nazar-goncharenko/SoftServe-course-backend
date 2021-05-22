package com.softserve.app.service.configService;

import com.softserve.app.models.Config;
import com.softserve.app.repository.ConfigRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfigService {

    private final ConfigRepository configRepository;

    public Config getById(Long id){
        return configRepository.getById(id);
    }

    public void save(Config config){
        configRepository.save(config);
    }

    public Config getTop(){
        return configRepository.getTopByOrderByIdDesc();
    }
}
