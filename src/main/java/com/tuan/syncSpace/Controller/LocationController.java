package com.tuan.syncSpace.Controller;

import com.tuan.syncSpace.Dto.Request.LocationDtoRequest;
import com.tuan.syncSpace.Dto.Response.LocationDtoResponse;
import com.tuan.syncSpace.Mapper.LocationMapper;
import com.tuan.syncSpace.Service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.redis.connection.ReactiveStreamCommands.AddStreamRecord.body;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLocation(
            @Valid @RequestBody LocationDtoRequest request
    ) {
        var location = locationService.createLocation(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(locationMapper.LocationEntityToLocationDtoResponse(location));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocation(
            @PathVariable UUID id
    ) {
        var res = locationMapper.LocationEntityToLocationDtoResponse(locationService.getLocationById(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping
    public ResponseEntity<?> getAllLocations(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        var locations = locationService.getAllLocations(page, size);
        return ResponseEntity.ok(
                locations.map(locationMapper::LocationEntityToLocationDtoResponse)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateLocation(
            @PathVariable UUID id,
            @Valid @RequestBody LocationDtoRequest request
    ) {
        var res = locationMapper.LocationEntityToLocationDtoResponse(locationService.updateLocation(id, request));
        return ResponseEntity.status(HttpStatus.OK)
                .body(res);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteLocation(
            @PathVariable UUID id
    ) {
        locationService.deleteLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
