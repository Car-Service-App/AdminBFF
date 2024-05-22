package ru.vsu.cs.zmaev.adminbff.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Описание класса CarConfigRequestDto")
public class CarConfigRequestDto {
    @Schema(description = "Id двигателя", example = "1")
    private Long engineId;
    @Schema(description = "Id трансмиссии", example = "1")
    private Long transmissionId;
}
