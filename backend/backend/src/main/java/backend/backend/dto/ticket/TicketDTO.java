package backend.backend.dto.ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import backend.backend.enums.TicketStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDTO {
    private Long id;
    private String ticketCode;
    private Long bookingId;
    private String bookingCode;
    private Long tripId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Long seatId;
    private String seatNumber;
    private Long passengerId;
    private String passengerName;
    private BigDecimal price;
    private TicketStatus status;
    private String pickupLocation;
    private String dropoffLocation;
}
