package backend.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleRegisterDTO {

    @NotBlank
    @Size(max = 8192)
    private String credential;
}
