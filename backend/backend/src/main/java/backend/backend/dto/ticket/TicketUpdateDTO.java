package backend.backend.dto.ticket;

import java.math.BigDecimal;

import backend.backend.enums.TicketStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketUpdateDTO {
    @NotNull(message = "Booking ID is required")
    @Positive(message = "Booking ID must be a positive number")
    private Long bookingId;

    @NotNull(message = "Trip ID is required")
    @Positive(message = "Trip ID must be a positive number")
    private Long tripId;

    @NotNull(message = "Seat ID is required")
    @Positive(message = "Seat ID must be a positive number")
    private Long seatId;

    @NotNull(message = "Passenger ID is required")
    @Positive(message = "Passenger ID must be a positive number")
    private Long passengerId;

    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "0.00", message = "Ticket price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Ticket status is required")
    private TicketStatus status;

    private String pickupLocation;
    private String dropoffLocation;
}
