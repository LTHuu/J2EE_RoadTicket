package backend.backend.entity;

import backend.backend.enums.StaffPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table 
(name = "user_companies",
    uniqueConstraints = @UniqueConstraint (
        columnNames = {"user_id", "company_id"}
    )
)
@Getter 
@Setter 
public class User_BusCom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private BusCompany busCompany;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private StaffPosition position;

    @Column(nullable = false)
    private Boolean active = true;
}
