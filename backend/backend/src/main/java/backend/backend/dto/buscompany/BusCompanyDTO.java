package backend.backend.dto.buscompany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusCompanyDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
    private String hotline;
    private String email;
    private String address;
    private String logoUrl;
    private String status;
}
