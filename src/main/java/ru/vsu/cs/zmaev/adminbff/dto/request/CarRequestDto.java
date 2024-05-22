package ru.vsu.cs.zmaev.adminbff.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.vsu.cs.zmaev.adminbff.dto.enums.CarType;

import java.util.List;

@Data
@Schema(description = "Описание класса CarRequestDto")
public class CarRequestDto {
    @Schema(description = "Тип автомобиля", example = "CABRIOLET")
    private final CarType carType;
    @Schema(description = "Год выпуска автомобиля")
    private final Integer releaseYear;
    @Schema(description = "Модель автомобиля", example = "1")
    private final Long carModelId;
    @Schema(description = "Конфигурации автомобиля")
    private final List<CarConfigRequestDto> carConfigs;
    @Schema(description = "Поколение автомобиля", example = "1")
    private final Integer generation;
    @Schema(description = "Является ли модель рестайлингом", example = "false")
    private final Boolean isRestyling;
    @Schema(description = "Id Трансмиссии автомобиля", example = "cars/bmw/m3.img")
    private final String carImageLink;
}
