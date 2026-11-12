package backend.backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import backend.backend.dto.ticket.TicketCreateDTO;
import backend.backend.dto.ticket.TicketDTO;
import backend.backend.dto.ticket.TicketSearchDTO;
import backend.backend.dto.ticket.TicketUpdateDTO;
import backend.backend.entity.Booking;
import backend.backend.entity.Passenger;
import backend.backend.entity.Seat;
import backend.backend.entity.Ticket;
import backend.backend.entity.TripInfo;
import backend.backend.mapper.TicketMapper;
import backend.backend.repository.BookingRepository;
import backend.backend.repository.PassengerRepository;
import backend.backend.repository.SeatRepository;
import backend.backend.repository.TicketRepository;
import backend.backend.repository.TripInfoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private final TripInfoRepository tripInfoRepository;
    private final SeatRepository seatRepository;
    private final PassengerRepository passengerRepository;
    private final TicketMapper ticketMapper;

    @Transactional(readOnly = true)
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketDTO getTicketById(Long id) {
        return ticketMapper.toDTO(findTicket(id));
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> getTicketsByBookingId(Long bookingId) {
        return ticketRepository.findAllByBooking_Id(bookingId)
                .stream()
                .map(ticketMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> getTicketsByTripId(Long tripId) {
        return ticketRepository.findAllByTrip_Id(tripId)
                .stream()
                .map(ticketMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketDTO> searchTickets(TicketSearchDTO dto) {
        return ticketRepository.searchTickets(
                        dto.getBookingId(),
                        dto.getTripId(),
                        dto.getSeatId(),
                        dto.getPassengerId(),
                        dto.getStatus(),
                        normalizeSearchText(dto.getTicketCode()),
                        normalizeSearchText(dto.getPickupLocation()),
                        normalizeSearchText(dto.getDropoffLocation()))
                .stream()
                .map(ticketMapper::toDTO)
                .toList();
    }

    public TicketDTO createTicket(TicketCreateDTO dto) {
        Ticket ticket = ticketMapper.toEntity(dto);
        assignReferences(ticket, dto.getBookingId(), dto.getTripId(), dto.getSeatId(), dto.getPassengerId());
        if (dto.getStatus() != null) {
            ticket.setStatus(dto.getStatus());
        }
        return ticketMapper.toDTO(ticketRepository.save(ticket));
    }

    public TicketDTO updateTicket(Long id, TicketUpdateDTO dto) {
        Ticket ticket = findTicket(id);
        assignReferences(ticket, dto.getBookingId(), dto.getTripId(), dto.getSeatId(), dto.getPassengerId());
        ticket.setPrice(dto.getPrice());
        ticket.setStatus(dto.getStatus());
        ticket.setPickupLocation(dto.getPickupLocation());
        ticket.setDropoffLocation(dto.getDropoffLocation());
        return ticketMapper.toDTO(ticketRepository.save(ticket));
    }

    public void deleteTicket(Long id) {
        ticketRepository.delete(findTicket(id));
    }

    private void assignReferences(Ticket ticket, Long bookingId, Long tripId, Long seatId, Long passengerId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        TripInfo trip = tripInfoRepository.findById(tripId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found"));
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Passenger not found"));

        if (!seat.getVehicle().getId().equals(trip.getVehicle().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seat does not belong to the trip vehicle");
        }

        ticket.setBooking(booking);
        ticket.setTrip(trip);
        ticket.setSeat(seat);
        ticket.setPassenger(passenger);
    }

    private Ticket findTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
    }

    private String normalizeSearchText(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
