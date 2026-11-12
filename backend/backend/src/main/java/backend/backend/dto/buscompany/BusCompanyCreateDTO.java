package backend.backend.dto.buscompany;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusCompanyCreateDTO {
    @NotBlank(message = "Tên nhà xe không được để trống")
    private String name;

    private String description;
    private String code;
    private String hotline;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String address;
    private String logoUrl;
    private String status;
}
