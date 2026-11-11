package backend.backend.dto.ticket;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateTicketStatusRequest {
    @NotNull(message = "Trạng thái vé không được để trống")
    private String status;
}
