package backend.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import backend.backend.dto.ticket.TicketCreateDTO;
import backend.backend.dto.ticket.TicketDTO;
import backend.backend.entity.Ticket;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticketCode", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "trip", ignore = true)
    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "status", ignore = true)
    Ticket toEntity(TicketCreateDTO dto);

    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "booking.bookingCode", target = "bookingCode")
    @Mapping(source = "trip.id", target = "tripId")
    @Mapping(source = "trip.departureTime", target = "departureTime")
    @Mapping(source = "trip.arrivalTime", target = "arrivalTime")
    @Mapping(source = "seat.id", target = "seatId")
    @Mapping(source = "seat.seatNumber", target = "seatNumber")
    @Mapping(source = "passenger.id", target = "passengerId")
    @Mapping(source = "passenger.fullName", target = "passengerName")
    TicketDTO toDTO(Ticket ticket);
}
