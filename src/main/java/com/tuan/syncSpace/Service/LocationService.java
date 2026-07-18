package com.tuan.syncSpace.Service;

import com.tuan.syncSpace.Dto.Request.LocationDtoRequest;
import com.tuan.syncSpace.Entity.LocationEntity;
import com.tuan.syncSpace.Exception.AppException;
import com.tuan.syncSpace.Mapper.LocationMapper;
import com.tuan.syncSpace.Repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationEntity createLocation(LocationDtoRequest request) {
        try {
            return locationRepository.save(locationMapper.LocationDtoRequestToLocationEntity(request));
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Failed to create location: " + e.getMessage());
        }
    }

    public LocationEntity getLocationById(UUID id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Location not found"));
    }

    public Page<LocationEntity> getAllLocations(Integer page, Integer size) {
        if (page < 0 || size <= 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid page or size parameters");
        }
        Pageable pageable = PageRequest.of(page, size);
        return locationRepository.findAll(pageable);
    }

    @Transactional
    public LocationEntity updateLocation(UUID id, LocationDtoRequest request) {
        try {
            LocationEntity location = locationRepository.findLocationEntityById(id);
            return locationMapper.UpdateLocationDtoRequestToLocationEntity(request, location);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Failed to update location: " + e.getMessage());
        }
    }

    public void deleteLocation(UUID id) {
        locationRepository.deleteLocationEntityById(id);
    }
}
