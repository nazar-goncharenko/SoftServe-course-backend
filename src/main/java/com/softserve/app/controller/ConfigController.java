package com.softserve.app.controller;

import com.softserve.app.models.Config;
import com.softserve.app.service.configService.ConfigService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    @PostMapping("/config/set")
    public void setSetup(@RequestBody Config config){
        configService.save(config);
    }

    @GetMapping("/config/get")
    public Config getSetup(){
        return configService.getTop();
    }
}
