package ru.vsu.cs.zmaev.adminbff.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CarWithConfigResponseDto {
    private Long id;
    private String manufacturerName;
    private String modelName;
    private Integer releaseYear;
    private String country;
    private String carImageLink;
    private List<CarConfigResponseDto> config;
}