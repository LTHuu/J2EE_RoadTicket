package backend.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.dto.vehicle.VehicleCreateDTO;
import backend.backend.dto.vehicle.VehicleDTO;
import backend.backend.dto.vehicle.VehicleSearchDTO;
import backend.backend.dto.vehicle.VehicleUpdateDTO;
import backend.backend.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/search")
    public ResponseEntity<List<VehicleDTO>> searchVehicles(@ModelAttribute VehicleSearchDTO search) {
        return ResponseEntity.ok(vehicleService.searchVehicles(search));
    }

    @GetMapping("/bus-company/{busCompanyId}")
    public ResponseEntity<List<VehicleDTO>> getVehiclesByBusCompanyId(@PathVariable Long busCompanyId) {
        return ResponseEntity.ok(vehicleService.getVehiclesByBusCompanyId(busCompanyId));
    }

    @GetMapping("/license-plate/{licensePlate}")
    public ResponseEntity<VehicleDTO> getVehicleByLicensePlate(@PathVariable String licensePlate) {
        return ResponseEntity.ok(vehicleService.getVehicleByLicensePlate(licensePlate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> addVehicle(@Valid @RequestBody VehicleCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.addVehicle(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleUpdateDTO dto) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
