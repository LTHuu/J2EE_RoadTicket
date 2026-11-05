
package backend.backend.repository;

import backend.backend.entity.Ticket;
import backend.backend.enums.TicketStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TicketRepository
        extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByTicketCode(String ticketCode);

    boolean existsByTicketCode(String ticketCode);

    List<Ticket> findByBookingId(Long bookingId);

    List<Ticket> findByTripId(Long tripId);

    List<Ticket> findByPassengerId(Long passengerId);

    List<Ticket> findByStatus(TicketStatus status);

    boolean existsByTripIdAndSeatIdAndStatus(
            Long tripId,
            Long seatId,
            TicketStatus status
    );
}
