package ru.vsu.cs.zmaev.adminbff.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса EngineResponseDto")
public class EngineResponseDto {
    @Schema(description = "Id двигателя", example = "1")
    private final Long id;
    @Schema(description = "Название двигателя", example = "EP6")
    private final String name;
    @Schema(description = "Объем двигателя", example = "1.6")
    private final String capacity;
}
