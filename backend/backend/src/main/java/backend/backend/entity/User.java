package backend.backend.entity;

import backend.backend.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table (name = "users")
@Getter 
@Setter 
public class User {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true, unique = true)
    @Email 
    private String email;

    @Column (nullable = true, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column (nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private UserRole role = UserRole.CUSTOMER;
}
