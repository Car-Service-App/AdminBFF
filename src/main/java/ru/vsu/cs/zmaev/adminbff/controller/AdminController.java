package ru.vsu.cs.zmaev.adminbff.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.zmaev.adminbff.dto.AbstractPage;
import ru.vsu.cs.zmaev.adminbff.dto.enums.CarType;
import ru.vsu.cs.zmaev.adminbff.dto.request.*;
import ru.vsu.cs.zmaev.adminbff.dto.response.*;
import ru.vsu.cs.zmaev.adminbff.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {

    private final AdminService adminService;

    @GetMapping(value = "car/")
    public ResponseEntity<AbstractPage<CarResponseDto>> findAllCars(
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize
    ) {
        AbstractPage<CarResponseDto> carResponseDtoPage = adminService.findAllCars(PageRequest.of(pagePosition, pageSize));
        return ResponseEntity.status(HttpStatus.OK).body(carResponseDtoPage);
    }

    @PostMapping(value = "car/")
    public ResponseEntity<CarResponseDto> createCar(
            @Validated @RequestBody CarRequestDto requestDto
    ) {
        CarResponseDto dto = adminService.createCar(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping(value = "manufacturer/")
    public ResponseEntity<List<ManufacturerResponseDto>> findAllManufacturersNoPagination() {
        List<ManufacturerResponseDto> dtos = adminService.findAllManufacturers();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping(value = "models/manufacturer/{id}")
    public ResponseEntity<List<CarModelResponseDto>> findAllModelsByManufacturerId(@PathVariable Long id) {
        List<CarModelResponseDto> dtos = adminService.findAllModelsByManufacturer(id);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping(value = "car-type/")
    public ResponseEntity<List<CarType>> findAllCarTypes() {
        List<CarType> dtos = adminService.findAllCarTypes();
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping(value = "car-parts/")
    public ResponseEntity<List<CarPartFromOzonResponseDto>> findAllByCarPartsRequestDto(
            @Valid @RequestBody FindCarPartsResponseDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adminService.findAllCarPartsDetails(requestDto));
    }

    @GetMapping(value = "engine/")
    public ResponseEntity<AbstractPage<EngineResponseDto>> findAllCarTypes(
            @RequestParam(defaultValue = "0") @Min(0) Integer pagePosition,
            @RequestParam(defaultValue = "10") @Min(1) Integer pageSize
    ) {
        AbstractPage<EngineResponseDto> dtos = adminService.findAllEngines(PageRequest.of(pagePosition, pageSize));
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping(value = "engine/")
    public ResponseEntity<EngineResponseDto> createEngine(
            @Validated @RequestBody CreateEngineRequestDto requestDto
    ) {
        EngineResponseDto dto = adminService.createEngine(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


    @GetMapping(value = "car/{id}/config")
    public ResponseEntity<CarWithConfigResponseDto> findCarWithConfigsById(
            @PathVariable Long id
    ) {
        CarWithConfigResponseDto dto = adminService.findCarWithConfigsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping(value = "car-job/car-config/{id}/all")
    public ResponseEntity<List<AllCarJobResponseDto>> findAllCarJobsByCarId(@PathVariable Long id) {
        List<AllCarJobResponseDto> dto = adminService.findAllCarJobsByCarConfig(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping(value = "car-job/car-part-on-marketplace/ozon/")
    public ResponseEntity<List<CarPartFromOzonResponseDto>> findAllCarJobsByCarId(FindCarPartInOzonRequestDto requestDto) {
        List<CarPartFromOzonResponseDto> dto = adminService.findCarPartsFromOzon(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping(value = "car-job/car-part-on-marketplace/")
    public ResponseEntity<Void> save(@Valid @RequestBody List<CarPartOnMarketplaceRequestDto> requestDto) {
         adminService.saveCarParts(requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
