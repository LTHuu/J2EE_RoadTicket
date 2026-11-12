package backend.backend.dto.UBCom;

import backend.backend.enums.StaffPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UBComDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long busCompanyId;
    private String busCompanyName;
    private StaffPosition position;
    private Boolean active;
}
