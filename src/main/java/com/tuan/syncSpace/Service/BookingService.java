package com.tuan.syncSpace.Service;

import com.tuan.syncSpace.Dto.Request.BookingDtoRequest;
import com.tuan.syncSpace.Dto.Request.BookingSlotDtoRequest;
import com.tuan.syncSpace.Entity.BookingEntity;
import com.tuan.syncSpace.Entity.BookingSlotEntity;
import com.tuan.syncSpace.Entity.UserEntity;
import com.tuan.syncSpace.Enum.PaymentStatus;
import com.tuan.syncSpace.Enum.WorkSpaceStatus;
import com.tuan.syncSpace.Exception.AppDetailException;
import com.tuan.syncSpace.Exception.AppException;
import com.tuan.syncSpace.Mapper.BookingSlotMapper;
import com.tuan.syncSpace.Repository.BookingRepository;
import com.tuan.syncSpace.Repository.BookingSlotRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingSlotMapper bookingSlotMapper;
    private final WorkSpaceService workSpaceService;
    private final BookingSlotRepository bookingSlotRepository;
    private final EntityManager entityManager;

    @Transactional
    public BookingEntity createBooking(BookingDtoRequest bookingDtoRequest) {

        for (BookingSlotDtoRequest bookingSlot : bookingDtoRequest.bookingSlots()) {
            var workspace = workSpaceService.getWorkSpaceById(bookingSlot.workspaceId());
            if (workspace.getStatus().equals(WorkSpaceStatus.ACTIVE)) {
                checkConditions(bookingSlot);
            } else {
                Map<String, String> errorDetails = new HashMap<>();
                errorDetails.put("message :", "WorkSpace is " + workspace.getStatus() );
                errorDetails.put("workspaceId: ", workspace.getId().toString());
                errorDetails.put("roomNumber: ", workspace.getRoomNumber());
                errorDetails.put("locationId: ", workspace.getLocationEntity().getId().toString());
                throw new AppDetailException(HttpStatus.BAD_REQUEST, errorDetails, "WorkSpace is " + workspace.getStatus() );
            }

        }

        var res = saveBooking(bookingDtoRequest);
        for (var s : res.getBookingSlots()) {
            s.setCost(0);
            s.setBookingEntity(
                    BookingEntity.builder()
                            .id(res.getId())
                            .build()
            );
        }

        bookingRepository.flush();
        entityManager.clear();
        BookingEntity bookingEntity = findById(res.getId());
        int sum = 0;
        for (var slot : bookingEntity.getBookingSlots()) {
            int cost = Calculator(slot);
            slot.setCost(cost);
            sum += cost;
        }
        bookingEntity.setTotalCost(sum);

        return bookingEntity;
    }

    int Calculator(
            BookingSlotEntity slot
    ) {
        long duration = Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes();
        return  (int)  (((float)duration / 60) * slot.getWorkSpaceEntity().getPricePerHour());
    }

    BookingEntity findById(UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Booking not found"));
    }

    void checkConditions(BookingSlotDtoRequest bookingSlot) {
        var ws = workSpaceService.getWorkSpaceById(bookingSlot.workspaceId());
        if (ws == null) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("message :", "Workspace is not exist");
            errorDetails.put("workspaceId: ", bookingSlot.workspaceId().toString());
            throw new AppDetailException(HttpStatus.BAD_REQUEST, errorDetails, "");
        }

        if (bookingSlot.startTime().isAfter(bookingSlot.endTime())){
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("message :", "Start time must be before end time");
            errorDetails.put("workspaceId: ", bookingSlot.workspaceId().toString());
            throw new AppDetailException(HttpStatus.BAD_REQUEST, errorDetails, "");
        }

        if (IsOverlapBooking(bookingSlot.startTime(), bookingSlot.endTime())){
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("message: ", "Booking slot overlaps with existing booking");
            errorDetails.put("workspaceId: ", bookingSlot.workspaceId().toString());
            throw new AppDetailException(HttpStatus.BAD_REQUEST, errorDetails,"");
        }

        if (!IsDivisibleBy30Minutes(bookingSlot.startTime(), bookingSlot.endTime())) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("message: ", "Booking slot duration must be divisible by 30 minutes");
            errorDetails.put("workspaceId: ", bookingSlot.workspaceId().toString());
            throw new AppDetailException(HttpStatus.BAD_REQUEST, errorDetails, "");
        }


    }

    BookingEntity saveBooking(BookingDtoRequest bookingDtoRequest) {
        List<BookingSlotEntity> bookingSlotEntities =
                bookingDtoRequest.bookingSlots()
                        .stream()
                        .map(bookingSlotMapper::BookingSlotDtoRequestToEntity)
                        .toList();

        return bookingRepository.save(
                BookingEntity.builder()
                        .user(UserEntity.builder()
                                .id(bookingDtoRequest.userId())
                                .build())
                        .paymentStatus(PaymentStatus.PENDING)
                        .totalCost(0)
                        .bookingSlots(bookingSlotEntities)
                        .build()
        );
    }

    Boolean IsOverlapBooking(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
         int cnt = bookingSlotRepository.validBooking(startTime, endTime);

         return cnt != 0;
    }

    Boolean IsDivisibleBy30Minutes(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        long minutes = Duration.between(startTime, endTime).toMinutes();
        return minutes % 30 == 0;
    }


    public Page<BookingEntity> getHistoryBooking(UUID userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findByUserId(userId, pageable);
    }
}
