package backend.backend.service;
import backend.backend.dto.ticket.TicketResponse;
import backend.backend.repository.TicketRepository;
import backend.backend.entity.Ticket;
import backend.backend.mapper.TicketMapper;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

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
}
