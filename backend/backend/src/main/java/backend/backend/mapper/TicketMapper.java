package backend.backend.mapper;

import backend.backend.dto.ticket.TicketRequest;
import backend.backend.dto.ticket.TicketResponse;
import backend.backend.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TicketMapper{

    // Mapping from Ticket entity to TicketResponse DTO
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

    // Mapping from TicketRequest DTO to Ticket entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticketCode", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Ticket toEntity(TicketRequest request);

    // Update existing Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ticketCode", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "trip", ignore = true)
    void updateEntity(TicketRequest request, @MappingTarget Ticket ticket);
}

