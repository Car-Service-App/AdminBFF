package ru.vsu.cs.zmaev.adminbff.dto.request;

import lombok.Data;

@Data
public class FindCarPartsResponseDto {
    private Long manufacturerId;
    private Long modelId;
    private Long carConfigId;
    private String carJobName;
}
