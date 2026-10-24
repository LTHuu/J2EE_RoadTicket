
package backend.backend.entity;

import backend.backend.enums.AuthProvider;
import backend.backend.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    // Có thể null với tài khoản Google
    // Nếu có mật khẩu thì phải lưu BCrypt hash
    @Column(nullable = true)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.CUSTOMER;

    // Email đã được xác minh hay chưa
    @Column(nullable = false)
    private boolean emailVerified = false;

    // Phương thức đăng nhập chính
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider = AuthProvider.LOCAL;
}
