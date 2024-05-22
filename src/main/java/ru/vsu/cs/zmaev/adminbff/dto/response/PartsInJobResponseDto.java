package ru.vsu.cs.zmaev.adminbff.dto.response;

import lombok.Data;

@Data
public class PartsInJobResponseDto {
    private Long id;
    private Long carJobId;
    private Long carPartId;
}
