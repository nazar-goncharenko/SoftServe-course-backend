package com.softserve.app.dto;

import com.softserve.app.constant.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ConfigDTO {
    private Long id;
    private Period period;
    private boolean showMostPopular;
}
