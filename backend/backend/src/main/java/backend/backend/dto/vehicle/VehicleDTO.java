package backend.backend.dto.vehicle;

import backend.backend.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {
    private Long id;
    private Long busCompanyId;
    private String busCompanyName;
    private String licensePlate;
    private String name;
    private VehicleType type;
    private int seatCapacity;
    private Boolean active;
}
