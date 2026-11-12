package backend.backend.dto.vehicle;

import backend.backend.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleSearchDTO {
    private String text;
    private VehicleType type;
    private Boolean active;
}
