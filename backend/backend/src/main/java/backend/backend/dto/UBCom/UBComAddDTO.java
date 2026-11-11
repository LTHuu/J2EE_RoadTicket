package backend.backend.dto.UBCom;

import backend.backend.enums.StaffPosition;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UBComAddDTO {

    @NotNull(message = "Người dùng không được để trống")
    private Long userId;

    @NotNull(message = "Công ty xe không được để trống")
    private Long busCompanyId;

    @NotNull(message = "Vị trí công việc không được để trống")
    private StaffPosition position;

    private Boolean active = true;
}
