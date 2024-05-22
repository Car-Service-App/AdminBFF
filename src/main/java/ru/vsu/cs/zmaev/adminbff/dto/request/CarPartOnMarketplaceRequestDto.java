package ru.vsu.cs.zmaev.adminbff.dto.request;

import lombok.Data;

@Data
public class CarPartOnMarketplaceRequestDto {
    private final Long marketplaceId;
    private final String name;
    private final Long carPartId;
    private final Boolean isOriginal;
    private final Double lastPrice;
    private final String url;
    private final String imageLink;
}
