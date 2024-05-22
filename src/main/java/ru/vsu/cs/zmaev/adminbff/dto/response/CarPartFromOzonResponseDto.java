package ru.vsu.cs.zmaev.adminbff.dto.response;

import lombok.Data;

@Data
public class CarPartFromOzonResponseDto {
    private Long marketplaceId;
    private String marketplaceName;
    private String oem;
    private String name;
    private String imageLink;
    private Double lastPrice;
    private String url;
}
