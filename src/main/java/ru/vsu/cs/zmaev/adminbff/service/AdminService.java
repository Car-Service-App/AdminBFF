package ru.vsu.cs.zmaev.adminbff.service;

import org.springframework.data.domain.Pageable;
import ru.vsu.cs.zmaev.adminbff.dto.*;
import ru.vsu.cs.zmaev.adminbff.dto.enums.CarType;
import ru.vsu.cs.zmaev.adminbff.dto.request.*;
import ru.vsu.cs.zmaev.adminbff.dto.response.*;

import java.util.List;

public interface AdminService {
    AbstractPage<CarResponseDto> findAllCars(Pageable pageable);
    List<ManufacturerResponseDto> findAllManufacturers();
    List<CarModelResponseDto> findAllModelsByManufacturer(Long manufacturerId);
    List<CarType> findAllCarTypes();
    AbstractPage<EngineResponseDto> findAllEngines(Pageable pageable);
    EngineResponseDto createEngine(CreateEngineRequestDto createEngineRequestDto);
    CarResponseDto createCar(CarRequestDto requestDto);
    CarWithConfigResponseDto findCarWithConfigsById(Long id);
    List<AllCarJobResponseDto> findAllCarJobsByCarConfig(Long carConfigId);
    List<CarPartsInJobResponseDto> findAllCarPartsInJobByCarJob(FindCarPartsResponseDto responseDto);
    List<CarPartFromOzonResponseDto> findAllCarPartsDetails(FindCarPartsResponseDto responseDto);
    List<CarPartFromOzonResponseDto> findCarPartsFromOzon(FindCarPartInOzonRequestDto requestDto);
    void saveCarParts(List<CarPartOnMarketplaceRequestDto> requestDto);
}
