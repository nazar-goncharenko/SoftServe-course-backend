package com.softserve.app.controller;

import com.softserve.app.constant.Period;
import com.softserve.app.dto.ArticleDTO;
import com.softserve.app.models.Config;
import com.softserve.app.service.configService.ConfigService;
import com.softserve.app.service.mostPopularService.MostPopularService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class MostPopularController {

    private final ConfigService configService;

    private final MostPopularService mostPopularService;

    @GetMapping("/mostPopular/{location}")
    public List<ArticleDTO> getLocation(@PathVariable("location") String location){
        return mostPopularService.getMostPopular(location);
    }

    @PostMapping("/mostPopular/period")
    public void getPeriod(@RequestBody String period){
        Config config = new Config();
        config.setPeriod(Period.getPeriodDate(period));
        configService.save(config);
    }
}
