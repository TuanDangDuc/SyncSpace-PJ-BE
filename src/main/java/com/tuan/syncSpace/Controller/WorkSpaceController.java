package com.tuan.syncSpace.Controller;

import com.tuan.syncSpace.Dto.Request.WorkSpaceDtoRequest;
import com.tuan.syncSpace.Dto.Response.WorkSpaceDtoResponse;
import com.tuan.syncSpace.Mapper.WorkSpaceMapper;
import com.tuan.syncSpace.Service.WorkSpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspace")
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;
    private final WorkSpaceMapper workSpaceMapper;

    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createWorkSpace(
            @Valid @RequestBody WorkSpaceDtoRequest request
    ) {
        var workSpace = workSpaceService.createWorkSpace(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workSpaceMapper.WorkSpaceEntityToWorkSpaceDtoResponse(workSpace));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkSpace(
            @PathVariable UUID id
    ) {
        var workSpace = workSpaceService.getWorkSpaceById(id);
        return ResponseEntity.ok(
                workSpaceMapper.WorkSpaceEntityToWorkSpaceDtoResponse(workSpace)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllWorkSpaces(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        var workSpaces = workSpaceService.getAllWorkSpaces(page, size);
        return ResponseEntity.ok(
                workSpaces.map(workSpaceMapper::WorkSpaceEntityToWorkSpaceDtoResponse)
        );
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<?> getWorkSpacesByLocation(
            @PathVariable UUID locationId
    ) {
        var workSpaces = workSpaceService.getWorkSpacesByLocation(locationId);
        return ResponseEntity.ok(
                workSpaces.stream()
                        .map(workSpaceMapper::WorkSpaceEntityToWorkSpaceDtoResponse)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateWorkSpace(
            @PathVariable UUID id,
            @Valid @RequestBody WorkSpaceDtoRequest request
    ) {
        var workSpace = workSpaceService.updateWorkSpace(id, request);
        return ResponseEntity.ok(
                workSpaceMapper.WorkSpaceEntityToWorkSpaceDtoResponse(workSpace)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteWorkSpace(
            @PathVariable UUID id
    ) {
        workSpaceService.deleteWorkSpace(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
