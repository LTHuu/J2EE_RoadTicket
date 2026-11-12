package backend.backend.dto.trip;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import backend.backend.enums.TripStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripDTO {
    private Long id;
    private Long routeId;
    private String departureLocation;
    private String arrivalLocation;
    private Long vehicleId;
    private String vehicleName;
    private String licensePlate;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal ticketPrice;
    private TripStatus status;
}
