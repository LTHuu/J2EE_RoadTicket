package backend.backend.controller;

import backend.backend.dto.buscompany.BusCompanyCreateDTO;
import backend.backend.dto.buscompany.BusCompanyDTO;
import backend.backend.dto.buscompany.BusCompanyUpdateDTO;
import backend.backend.service.BusCompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/bus-companies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BusCompanyController {

    private final BusCompanyService busCompanyService;

    @GetMapping
    public ResponseEntity<List<BusCompanyDTO>> getAllBusCompanies() {
        return ResponseEntity.ok(busCompanyService.getAllBusCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusCompanyDTO> getBusCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(busCompanyService.getBusCompanyById(id));
    }

    @PostMapping
    public ResponseEntity<BusCompanyDTO> createBusCompany(@Valid @RequestBody BusCompanyCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(busCompanyService.createBusCompany(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusCompanyDTO> updateBusCompany(
            @PathVariable Long id,
            @Valid @RequestBody BusCompanyUpdateDTO dto) {
        return ResponseEntity.ok(busCompanyService.updateBusCompany(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusCompany(@PathVariable Long id) {
        busCompanyService.deleteBusCompany(id);
        return ResponseEntity.noContent().build();
    }
}