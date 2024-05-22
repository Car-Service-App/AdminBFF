package ru.vsu.cs.zmaev.adminbff.dto.request;

import lombok.Data;

@Data
public class CreateJobRequestDto {
    private Long manufacturerId;
    private Long modelId;
    private Long configId;
    private String jobName;
    private String oem;
}
