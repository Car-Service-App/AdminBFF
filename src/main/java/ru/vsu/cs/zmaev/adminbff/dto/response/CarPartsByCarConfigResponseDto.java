package ru.vsu.cs.zmaev.adminbff.dto.response;

import lombok.Data;

@Data
public class CarPartsByCarConfigResponseDto {
    private Long id;
    private String carPartName;
    private String oem;
    private String link;
    private String lastPrice;
}
