package backend.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "routes")
@Getter
@Setter
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String departureLocation;

    @NotBlank
    @Column(nullable = false)
    private String arrivalLocation;

    @Positive
    @Column(precision = 10, scale = 2)
    private BigDecimal distanceKm;

    @Positive
    private Integer estimatedDurationMinutes;

    @Column(nullable = false)
    private Boolean active = true;
}
