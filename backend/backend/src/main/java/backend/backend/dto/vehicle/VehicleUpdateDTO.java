package backend.backend.dto.vehicle;

import backend.backend.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleUpdateDTO {
    @NotNull(message = "Bus company ID is required")
    @Positive(message = "Bus company ID must be a positive number")
    private Long busCompanyId;

    @NotBlank(message = "License plate cannot be blank")
    private String licensePlate;
    private String name;
    private VehicleType type = VehicleType.SEATER;

    @Positive(message = "Seating capacity must be a positive number")
    private int seatCapacity;
    private boolean active = true;
}
