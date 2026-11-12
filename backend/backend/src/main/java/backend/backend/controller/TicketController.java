package backend.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.dto.ticket.TicketCreateDTO;
import backend.backend.dto.ticket.TicketDTO;
import backend.backend.dto.ticket.TicketSearchDTO;
import backend.backend.dto.ticket.TicketUpdateDTO;
import backend.backend.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TicketDTO>> searchTickets(@ModelAttribute TicketSearchDTO search) {
        return ResponseEntity.ok(ticketService.searchTickets(search));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ticketService.getTicketsByBookingId(bookingId));
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<TicketDTO>> getTicketsByTripId(@PathVariable Long tripId) {
        return ResponseEntity.ok(ticketService.getTicketsByTripId(tripId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketUpdateDTO dto) {
        return ResponseEntity.ok(ticketService.updateTicket(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
