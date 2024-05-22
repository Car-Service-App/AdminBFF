package ru.vsu.cs.zmaev.adminbff.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса для создания двигателя")
public class CreateEngineRequestDto {
    @Schema(description = "Название двигателя", example = "EP6")
    private final String name;
    @Schema(description = "Объем двигателя", example = "1.6")
    private final String capacity;
}
