package com.osla.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class OutputIngredient {
    private String name;
    private int count;

    //Apparently must have for Hibernate
    public OutputIngredient(String name, Long count) {
        this.name = name;
        this.count = Math.toIntExact(count);
    }
}
