package backend.backend.dto.ticket;

import java.math.BigDecimal;

public record TicketResponse(
    Long id,
    String ticketCode,
    String bookingCode,
    Long tripId,
    String seatNumber,
    String passengerName,
    BigDecimal price,
    String status,
    String pickupLocation,
    String dropoffLocation
) {
}