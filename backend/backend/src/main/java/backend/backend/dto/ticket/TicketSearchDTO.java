package backend.backend.dto.ticket;

import backend.backend.enums.TicketStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketSearchDTO {
    private Long bookingId;
    private Long tripId;
    private Long seatId;
    private Long passengerId;
    private TicketStatus status;
    private String ticketCode;
    private String pickupLocation;
    private String dropoffLocation;
}
