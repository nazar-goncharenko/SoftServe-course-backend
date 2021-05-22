package com.softserve.app.dto;

import com.softserve.app.models.Estimation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class EstimationDTO {

    Long id;

    UserDTO user;

    boolean like;

    public Estimation ofEntity(){
        return Estimation.builder()
                .id(this.id)
                .user(this.user.ofEntity())
                .dislike(this.like)
                .build();
    }
}
