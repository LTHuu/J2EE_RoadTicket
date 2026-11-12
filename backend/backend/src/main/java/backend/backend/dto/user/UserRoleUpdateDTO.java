package backend.backend.dto.user;

import backend.backend.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleUpdateDTO {
    @NotBlank(message = "Vai trò người dùng không được để trống")
    private UserRole role;
}
