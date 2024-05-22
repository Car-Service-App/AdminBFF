package ru.vsu.cs.zmaev.adminbff.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarModelResponseDto")
public class CarModelResponseDto {
    private final Long id;
    @Schema(description = "Модель автомобиля", example = "Civic")
    private final String modelName;
    @Schema(description = "Производитель")
    private final String manufacturerName;
}

