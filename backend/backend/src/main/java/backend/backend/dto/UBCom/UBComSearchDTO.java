package backend.backend.dto.UBCom;

import backend.backend.enums.StaffPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UBComSearchDTO {
    private String searchText;
    private Long userId;
    private Long busCompanyId;
    private StaffPosition position;
    private Boolean active;
}
