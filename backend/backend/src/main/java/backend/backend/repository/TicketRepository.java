package backend.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import backend.backend.entity.Ticket;
import backend.backend.enums.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByBooking_Id(Long bookingId);

    List<Ticket> findAllByTrip_Id(Long tripId);

    @Query("""
            SELECT t FROM Ticket t
            WHERE (:bookingId IS NULL OR t.booking.id = :bookingId)
              AND (:tripId IS NULL OR t.trip.id = :tripId)
              AND (:seatId IS NULL OR t.seat.id = :seatId)
              AND (:passengerId IS NULL OR t.passenger.id = :passengerId)
              AND (:status IS NULL OR t.status = :status)
              AND (:ticketCode IS NULL
                   OR LOWER(t.ticketCode) LIKE LOWER(CONCAT('%', :ticketCode, '%')))
              AND (:pickupLocation IS NULL
                   OR LOWER(t.pickupLocation) LIKE LOWER(CONCAT('%', :pickupLocation, '%')))
              AND (:dropoffLocation IS NULL
                   OR LOWER(t.dropoffLocation) LIKE LOWER(CONCAT('%', :dropoffLocation, '%')))
            ORDER BY t.id DESC
            """)
    List<Ticket> searchTickets(
            @Param("bookingId") Long bookingId,
            @Param("tripId") Long tripId,
            @Param("seatId") Long seatId,
            @Param("passengerId") Long passengerId,
            @Param("status") TicketStatus status,
            @Param("ticketCode") String ticketCode,
            @Param("pickupLocation") String pickupLocation,
            @Param("dropoffLocation") String dropoffLocation);
}
