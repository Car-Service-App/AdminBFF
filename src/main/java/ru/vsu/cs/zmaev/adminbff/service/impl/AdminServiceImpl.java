package ru.vsu.cs.zmaev.adminbff.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import ru.vsu.cs.zmaev.adminbff.dto.AbstractPage;
import ru.vsu.cs.zmaev.adminbff.dto.enums.CarType;
import ru.vsu.cs.zmaev.adminbff.dto.request.*;
import ru.vsu.cs.zmaev.adminbff.dto.response.*;
import ru.vsu.cs.zmaev.adminbff.exception.NoSuchEntityException;
import ru.vsu.cs.zmaev.adminbff.service.AdminService;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final WebClient webClient;

    @Value("${car.service.base-url}")
    private String carServiceBaseUrl;

    @Value("${marketplace.service.base-url}")
    private String marketplaceServiceBaseUrl;

    @Value("${parser.service.base-url}")
    private String parserServiceBaseUrl;

    @Override
    @Transactional(readOnly = true)
    public AbstractPage<CarResponseDto> findAllCars(Pageable pageable) {
        String url = UriComponentsBuilder.fromUriString(String.format("%s/car", carServiceBaseUrl))
                .queryParam("pagePosition", pageable.getPageNumber())
                .queryParam("pageSize", pageable.getPageSize())
                .toUriString();
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(new ParameterizedTypeReference<AbstractPage<CarResponseDto>>() {})
                .block();
    }

    @Override
    @Transactional
    public CarResponseDto createCar(CarRequestDto requestDto) {
        return webClient
                .post()
                .uri(String.format("%s/car", carServiceBaseUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NoSuchEntityException("No such entity"));
                    } else {
                        return Mono.error(new RuntimeException("Client error"));
                    }
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(CarResponseDto.class)
                .block();
    }

    @Override
    public CarWithConfigResponseDto findCarWithConfigsById(Long id) {
        String url = String.format("%s/car/%s/config", carServiceBaseUrl, id);
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NoSuchEntityException("No such entity"));
                    } else {
                        return Mono.error(new RuntimeException("Client error"));
                    }
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(CarWithConfigResponseDto.class)
                .block();
    }

    @Override
    public List<AllCarJobResponseDto> findAllCarJobsByCarConfig(Long carConfigId) {
        String url = String.format("%s/car-job/car-config/%s/all", carServiceBaseUrl, carConfigId);
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NoSuchEntityException("No such entity"));
                    } else {
                        return Mono.error(new RuntimeException("Client error"));
                    }
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(new ParameterizedTypeReference<List<AllCarJobResponseDto>>() {})
                .block();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarPartsInJobResponseDto> findAllCarPartsInJobByCarJob(FindCarPartsResponseDto responseDto) {
        String url = String.format("%s/parts-in-job/car-job", carServiceBaseUrl);
        return webClient
                .post()
                .uri(url)
                .bodyValue(responseDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NoSuchEntityException("No such entity"));
                    } else {
                        return Mono.error(new RuntimeException("Client error"));
                    }
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(new ParameterizedTypeReference<List<CarPartsInJobResponseDto>>() {})
                .block();
    }

    @Override
    public List<CarPartFromOzonResponseDto> findAllCarPartsDetails(FindCarPartsResponseDto responseDto) {
        List<CarPartsInJobResponseDto> carPartsInJobResponseDtos = findAllCarPartsInJobByCarJob(responseDto);
        List<Long> carPartIds = carPartsInJobResponseDtos.stream()
                .map(CarPartsInJobResponseDto::getCarPartId)
                .toList();
        String url = String.format("%s/car-part-on-marketplace/car-parts", marketplaceServiceBaseUrl);
        return webClient
                .post()
                .uri(url)
                .bodyValue(carPartIds)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NoSuchEntityException("No such entity"));
                    } else {
                        return Mono.error(new RuntimeException("Client error"));
                    }
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(new ParameterizedTypeReference<List<CarPartFromOzonResponseDto>>() {})
                .block();
    }

    public void saveAllCarPartsFromOzon() {
        // Сохраняем в маркеплейс сервисе
        // Сохраняем в кар партс сервисе, если есть то возвращаем, что есть ендпоинт saveOzonParts
        // Связываем детали с работой и конфигурациями
    }

    @Override
    @Transactional
    public List<CarPartFromOzonResponseDto> findCarPartsFromOzon(FindCarPartInOzonRequestDto requestDto) {
        String search = requestDto.toOzonSearch();
        String url = String.format("%s/car-part-on-marketplace/ozon/%s", marketplaceServiceBaseUrl, search);
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(new ParameterizedTypeReference<List<CarPartFromOzonResponseDto>>() {})
                .block();
    }

    @Override
    public void saveCarParts(List<CarPartOnMarketplaceRequestDto> requestDto) {
        String url = String.format("%s/car-part-on-marketplace", marketplaceServiceBaseUrl);
        webClient
                .post()
                .uri(url)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManufacturerResponseDto> findAllManufacturers() {
        String url = String.format("%s/manufacturers/no-pagination", carServiceBaseUrl);
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(new ParameterizedTypeReference<List<ManufacturerResponseDto>>() {})
                .block();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarModelResponseDto> findAllModelsByManufacturer(Long manufacturerId) {
        String url = String.format("%s/car-models/manufacturer/%s", carServiceBaseUrl, manufacturerId);
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(new ParameterizedTypeReference<List<CarModelResponseDto>>() {})
                .block();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarType> findAllCarTypes() {
        return Arrays.asList(CarType.values());
    }

    @Override
    @Transactional
    public EngineResponseDto createEngine(CreateEngineRequestDto requestDto) {
        return webClient
                .post()
                .uri(String.format("%s/engine", carServiceBaseUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new NoSuchEntityException("No such entity"));
                    } else {
                        return Mono.error(new RuntimeException("Client error"));
                    }
                })
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(EngineResponseDto.class)
                .block();
    }

    @Override
    @Transactional(readOnly = true)
    public AbstractPage<EngineResponseDto> findAllEngines(Pageable pageable) {
        String url = UriComponentsBuilder.fromUriString(String.format("%s/engine", carServiceBaseUrl))
                .queryParam("pagePosition", pageable.getPageNumber())
                .queryParam("pageSize", pageable.getPageSize())
                .toUriString();
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Server error")))
                .bodyToMono(new ParameterizedTypeReference<AbstractPage<EngineResponseDto>>() {})
                .block();
    }
}
