package backend.backend.dto.trip;

import java.time.LocalDateTime;

import backend.backend.enums.TripStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripSearchDTO {
    private Long routeId;
    private Long vehicleId;
    private TripStatus status;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureFrom;
    private LocalDateTime departureTo;
    private LocalDateTime arrivalFrom;
    private LocalDateTime arrivalTo;
}
