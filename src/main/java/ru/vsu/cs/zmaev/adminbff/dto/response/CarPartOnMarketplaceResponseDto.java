package ru.vsu.cs.zmaev.adminbff.dto.response;

import lombok.Data;

@Data
public class CarPartOnMarketplaceResponseDto {
    private Long id;
    private Long marketplaceId;
    private String marketplaceName;
    private Long carPartId;
    private String name;
    private String imageLink;
    private Boolean isOriginal;
    private Double lastPrice;
    private String url;
}
