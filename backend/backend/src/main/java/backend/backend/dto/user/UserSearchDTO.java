package backend.backend.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDTO {
    String searchText;
    String role;
    Boolean emailVerified;
    String provider;
}
