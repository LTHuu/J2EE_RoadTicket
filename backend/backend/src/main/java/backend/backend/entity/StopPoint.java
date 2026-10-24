package backend.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "stop_point",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_stop_point_route_sequence",
        columnNames = {"route_id", "sequence_number"}
    )
)
@Getter
@Setter
public class StopPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String address;

    @NotNull
    @Positive
    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Column(nullable = false)
    private Boolean active = true;
}
