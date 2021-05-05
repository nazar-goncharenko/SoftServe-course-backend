package com.softserve.app.service.ConverterService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.app.constant.SportHubConstant;
import com.softserve.app.exception.SportHubException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConverterService {
    public <Clazz> Clazz convertStringToClass(String stringClass, Class<Clazz> clazz) {
        if (clazz.getAnnotation(JsonIgnoreProperties.class) == null) {
            log.error(
                    "{} exception on convertStringToClass method: {} haven't annotation JsonIgnoreProperties",
                    this.getClass(),
                    clazz
            );
            throw new SportHubException(SportHubConstant.CONVERTER_ANNOTATION_NOT_FOUND.getMessage(), 500);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(stringClass, clazz);
        } catch (Exception e) {
            throw new SportHubException(SportHubConstant.CONVERTER_PROCESS_EXCEPTION.getMessage(), 405);
        }
    }
}
