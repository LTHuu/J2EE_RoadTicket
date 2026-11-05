package backend.backend.mapper;

import backend.backend.dto.ticket.TicketResponse;
import backend.backend.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "ticketCode", source = "ticketCode")
    @Mapping(target = "bookingCode", source = "booking.bookingCode")
    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "seatNumber", source = "seat.seatNumber")
    @Mapping(target = "passengerName", source = "passenger.fullName")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "status", expression = "java(ticket.getStatus().name())")
    @Mapping(target = "pickupLocation", source = "pickupLocation")
    @Mapping(target = "dropoffLocation", source = "dropoffLocation")
    TicketResponse toResponse(Ticket ticket);

}