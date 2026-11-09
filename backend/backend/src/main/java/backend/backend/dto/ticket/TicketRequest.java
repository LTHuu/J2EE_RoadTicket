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
public class TicketRequest {
    @NotNull(message = "Mã đặt chỗ (Booking ID) không được để trống")
    @Positive(message = "Mã đặt chỗ phải là số dương")
    private Long bookingId;

    @NotNull(message = "Mã hành khách không được để trống")
    @Positive(message = "Mã hành khách phải là số dương")
    private Long passengerId;

    @NotNull(message = "Mã ghế không được để trống")
    @Positive(message = "Mã ghế phải là số dương")
    private Long seatId;

    @NotNull(message = "Mã chuyến đi không được để trống")
    @Positive(message = "Mã chuyến đi phải là số dương")
    private Long tripId;

    @NotNull(message = "Giá vé không được để trống")
    @DecimalMin(value = "0.01", message = "Giá vé phải lớn hơn 0")
    private BigDecimal price;

    @Size(max = 255, message = "Địa điểm đón không được vượt quá 255 ký tự")
    private String pickupLocation;

    @Size(max = 255, message = "Địa điểm trả không được vượt quá 255 ký tự")
    private String dropoffLocation;
}
