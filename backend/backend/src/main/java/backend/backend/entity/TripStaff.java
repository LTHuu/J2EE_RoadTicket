package backend.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import backend.backend.enums.StaffPosition;

@Entity
@Table(name = "trip_staff", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_id", "user_company_id"}))
@Getter
@Setter
public class TripStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private TripInfo trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_company_id", nullable = false)
    private User_BusCom staff;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffPosition position;
}
