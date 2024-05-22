package ru.vsu.cs.zmaev.adminbff.dto.response;

import lombok.Data;

@Data
public class CarConfigResponseDto {
    private Long id;
    private String engineName;
    private String engineCapacity;
    private String transmissionName;
}