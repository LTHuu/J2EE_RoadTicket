package backend.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bus_companies")
@Getter
@Setter
public class BusCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    //  Profile nhà xe
    private String code;
    private String hotline;
    private String email;
    
    @Column(columnDefinition = "TEXT")
    private String address;
    
    private String logoUrl;
    private String status; 
}