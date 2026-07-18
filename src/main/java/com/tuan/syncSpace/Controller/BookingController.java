package com.tuan.syncSpace.Controller;

import com.tuan.syncSpace.Dto.Request.BookingDtoRequest;
import com.tuan.syncSpace.Entity.BookingEntity;
import com.tuan.syncSpace.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestBody BookingDtoRequest bookingDtoRequest
    ) {
         var res = bookingService.createBooking(bookingDtoRequest);
         return res != null
                 ? ResponseEntity.status(HttpStatus.CREATED)
                 .body(res)
                 : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                 .build();
    }

    @GetMapping("get-history-booking/{userId}")
    public ResponseEntity<?> getHistoryBooking(
            @PathVariable UUID userId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size
    ) {
        var bookings = bookingService.getHistoryBooking(userId, page, size);
        return ResponseEntity.ok(bookings);
    }
}
