package backend.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "seats", uniqueConstraints = @UniqueConstraint(columnNames = {"vehicle_id", "seat_number"}))
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @NotBlank
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @Min(1)
    @Column(nullable = false)
    private Integer floor = 1;

    @Column(nullable = false)
    private Boolean active = true;
}
