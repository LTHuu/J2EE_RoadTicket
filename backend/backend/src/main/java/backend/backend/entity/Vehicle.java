package backend.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import backend.backend.enums.VehicleType;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private BusCompany busCompany;

    @NotBlank
    @Column(nullable = false, unique = true, length = 20)
    private String licensePlate;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type = VehicleType.SEATER;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer seatCapacity;

    @Column(nullable = false)
    private Boolean active = true;
}
