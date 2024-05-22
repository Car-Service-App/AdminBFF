package ru.vsu.cs.zmaev.adminbff.dto.request;

import lombok.Data;

@Data
public class FindCarPartInOzonRequestDto {
    private String markName;
    private String modelName;
    private String oem;

    public String toOzonSearch() {
        return markName + "+" + modelName + "+" + oem;
    }
}
