package ru.vsu.cs.zmaev.adminbff.dto.response;

import lombok.Data;

@Data
public class CarResponseDto {
    private final Long id;
    private final String manufacturerName;
    private final String modelName;
    private final Integer releaseYear;
    private final String type;
    private final String country;
}
