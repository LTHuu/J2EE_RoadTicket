package backend.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.dto.trip.TripCreateDTO;
import backend.backend.dto.trip.TripDTO;
import backend.backend.dto.trip.TripSearchDTO;
import backend.backend.dto.trip.TripUpdateDTO;
import backend.backend.service.TripInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripInfoController {
    private final TripInfoService tripInfoService;

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        return ResponseEntity.ok(tripInfoService.getAllTrips());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TripDTO>> searchTrips(@ModelAttribute TripSearchDTO search) {
        return ResponseEntity.ok(tripInfoService.searchTrips(search));
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<List<TripDTO>> getTripsByRouteId(@PathVariable Long routeId) {
        return ResponseEntity.ok(tripInfoService.getTripsByRouteId(routeId));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<TripDTO>> getTripsByVehicleId(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(tripInfoService.getTripsByVehicleId(vehicleId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
        return ResponseEntity.ok(tripInfoService.getTripById(id));
    }

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@Valid @RequestBody TripCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripInfoService.createTrip(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDTO> updateTrip(
            @PathVariable Long id,
            @Valid @RequestBody TripUpdateDTO dto) {
        return ResponseEntity.ok(tripInfoService.updateTrip(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripInfoService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
