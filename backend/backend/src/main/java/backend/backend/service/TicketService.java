package backend.backend.service;

import backend.backend.dto.ticket.TicketResponse;
import backend.backend.repository.TicketRepository;
import backend.backend.entity.Ticket;
import backend.backend.mapper.TicketMapper;
import backend.backend.dto.ticket.TicketRequest;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Transactional(readOnly = true)
    public TicketResponse getById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé với id: " + id));

        return ticketMapper.toResponse(ticket);
    }

    @Transactional (readOnly = true)
    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return tickets.stream()
                .map(ticketMapper::toResponse)
                .toList();
    }

    @Transactional
    public TicketResponse createTicket(TicketRequest request) {
        Ticket ticket = ticketMapper.toEntity(request);

        // Code xử lý business logic, cập nhật sau
        ticket.setBooking(null);
        ticket.setPassenger(null);
        ticket.setSeat(null);
        ticket.setTrip(null);

        // Ticket savedTicket = ticketRepository.save(ticket); // Bỏ cmt dòng này khi xử lý business logic xong
        return ticketMapper.toResponse(ticket); // Đổi sang savedTicket khi xử lý business logic xong
    }

    @Transactional
    public TicketResponse updateTicket(Long id, TicketRequest request) {

        Ticket existingTicket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy vé với id: " + id));

        ticketMapper.updateEntity(request, existingTicket);

        // Code xử lý business logic, cập nhật sau
        // existingTicket.setBooking(null);
        // existingTicket.setPassenger(null);
        // existingTicket.setSeat(null);
        // existingTicket.setTrip(null);

        return ticketMapper.toResponse(existingTicket);
    }

}
