package backend.backend.dto.user;

import backend.backend.enums.AuthProvider;
import backend.backend.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private UserRole role;
    private boolean emailVerified;
    private AuthProvider provider;
}
