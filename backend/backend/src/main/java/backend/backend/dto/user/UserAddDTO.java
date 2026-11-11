package backend.backend.dto.user;

import backend.backend.enums.UserRole;
import backend.backend.enums.AuthProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddDTO {

    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @Pattern(regexp = "^(0[35789])[0-9]{8}$", message = "Số điện thoại Việt Nam không hợp lệ")
    private String phone;

    @NotBlank(message = "Vai trò người dùng không được để trống")
    private UserRole role = UserRole.CUSTOMER;

    private boolean emailVerified = false;
    private AuthProvider provider = AuthProvider.LOCAL;

}
