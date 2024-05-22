package ru.vsu.cs.zmaev.adminbff.dto.response;

import lombok.*;

@Data
public class AllCarJobResponseDto {
    private Long carConfigId;
    private Long jobTypeId;
    private String jobTypeName;
    private Boolean isExist;
}