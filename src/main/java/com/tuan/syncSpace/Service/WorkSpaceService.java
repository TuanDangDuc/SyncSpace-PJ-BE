package com.tuan.syncSpace.Service;

import com.tuan.syncSpace.Dto.Request.WorkSpaceDtoRequest;
import com.tuan.syncSpace.Entity.LocationEntity;
import com.tuan.syncSpace.Entity.WorkSpaceEntity;
import com.tuan.syncSpace.Exception.AppException;
import com.tuan.syncSpace.Mapper.WorkSpaceMapper;
import com.tuan.syncSpace.Repository.LocationRepository;
import com.tuan.syncSpace.Repository.WorkSpaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    private final LocationRepository locationRepository;
    private final WorkSpaceMapper workSpaceMapper;

    public WorkSpaceEntity createWorkSpace(WorkSpaceDtoRequest request) {
        try {
            LocationEntity location = locationRepository.findById(request.locationId())
                    .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Location not found"));

            WorkSpaceEntity workSpaceEntity = workSpaceMapper.WorkSpaceDtoRequestToEntity(request);

            return workSpaceRepository.save(workSpaceEntity);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Failed to create work space: " + e.getMessage());
        }
    }

    public WorkSpaceEntity getWorkSpaceById(UUID id) {
        return workSpaceRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Work space not found"));
    }

    public Page<WorkSpaceEntity> getAllWorkSpaces(Integer page, Integer size) {
        if (page < 0 || size <= 0) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid page or size parameters");
        }
        Pageable pageable = PageRequest.of(page, size);
        return workSpaceRepository.findAll(pageable);
    }

    public List<WorkSpaceEntity> getWorkSpacesByLocation(UUID locationId) {
        locationRepository.findById(locationId)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Location not found"));
        return workSpaceRepository.findByLocationEntity_Id(locationId);
    }

    @Transactional
    public WorkSpaceEntity updateWorkSpace(UUID id, WorkSpaceDtoRequest request) {
        try {
            WorkSpaceEntity workSpaceEntity = getWorkSpaceById(id);
            return workSpaceMapper.UpdateWorkSpaceDtoRequestToEntity(request, workSpaceEntity);
        } catch (Exception e) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Failed to update work space: " + e.getMessage());
        }
    }

    public void deleteWorkSpace(UUID id) {
        WorkSpaceEntity workSpace = getWorkSpaceById(id);
        workSpaceRepository.delete(workSpace);
    }

}
