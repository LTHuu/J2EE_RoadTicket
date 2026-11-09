package backend.backend.controller;

import backend.backend.service.TicketService;
import jakarta.validation.Valid;
import backend.backend.dto.ticket.TicketResponse;
import backend.backend.dto.ticket.TicketRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;


@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        TicketResponse ticketResponse = ticketService.getById(id);
        return ResponseEntity.ok(ticketResponse);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        List<TicketResponse> ticketResponses = ticketService.getAllTickets();
        return ResponseEntity.ok(ticketResponses);
    }
    
    @PostMapping ("/create")
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketRequest request) {
        TicketResponse ticketResponse = ticketService.createTicket(request);
        return ResponseEntity.ok(ticketResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id, @Valid @RequestBody TicketRequest request) {
        TicketResponse ticketResponse = ticketService.updateTicket(id, request);
        return ResponseEntity.ok(ticketResponse);
    }
}
