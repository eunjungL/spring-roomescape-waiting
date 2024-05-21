package roomescape.reservation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.reservation.dto.MemberReservationResponse;
import roomescape.reservation.service.ReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    private final ReservationService reservationService;

    public AdminReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public MemberReservationResponse createReservation(@Valid @RequestBody ReservationCreateRequest request) {
        return reservationService.createReservation(request);
    }

    @GetMapping
    public List<MemberReservationResponse> readReservations() {
        return reservationService.readReservations();
    }

    @GetMapping("/search")
    public List<MemberReservationResponse> readReservations(
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo,
            @RequestParam Long memberId,
            @RequestParam Long themeId
    ) {
        return reservationService.searchReservations(dateFrom, dateTo, memberId, themeId);
    }

    @GetMapping("/waiting")
    public List<MemberReservationResponse> readWaitingReservations() {
        return reservationService.readWaitingReservations();
    }

    @PutMapping("/waiting/{id}")
    public void confirmWaitingReservation(@PathVariable Long id) {
        reservationService.confirmWaitingReservation(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
