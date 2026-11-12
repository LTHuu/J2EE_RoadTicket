package backend.backend.dto.trip;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import backend.backend.enums.TripStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripUpdateDTO {
    @NotNull(message = "Route ID is required")
    @Positive(message = "Route ID must be a positive number")
    private Long routeId;

    @NotNull(message = "Vehicle ID is required")
    @Positive(message = "Vehicle ID must be a positive number")
    private Long vehicleId;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "0.00", message = "Ticket price cannot be negative")
    private BigDecimal ticketPrice;

    @NotNull(message = "Trip status is required")
    private TripStatus status;

    @AssertTrue(message = "Arrival time must be after departure time")
    public boolean isScheduleValid() {
        return departureTime == null || arrivalTime == null || arrivalTime.isAfter(departureTime);
    }
}
