package com.softserve.app.models;

import com.softserve.app.constant.Period;
import com.softserve.app.dto.ConfigDTO;
import com.softserve.app.dto.HistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Дописати адміна який міняв

    // Дописати час коли міняв

    @Column(name = "period", nullable = false)
    private Period period;

    @Column(name = "showMostPopular", nullable = false)
    private boolean showMostPopular;

    // Інші настройки...

    public ConfigDTO ofDTO() {
        return ConfigDTO.builder()
                .id(this.id)
                .period(this.period)
                .showMostPopular(this.showMostPopular)
                .build();
    }
}
