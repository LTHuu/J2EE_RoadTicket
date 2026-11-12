package backend.backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import backend.backend.dto.trip.TripCreateDTO;
import backend.backend.dto.trip.TripDTO;
import backend.backend.dto.trip.TripSearchDTO;
import backend.backend.dto.trip.TripUpdateDTO;
import backend.backend.entity.Route;
import backend.backend.entity.TripInfo;
import backend.backend.entity.Vehicle;
import backend.backend.mapper.TripInfoMapper;
import backend.backend.repository.RouteRepository;
import backend.backend.repository.TripInfoRepository;
import backend.backend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TripInfoService {
    private final TripInfoRepository tripInfoRepository;
    private final RouteRepository routeRepository;
    private final VehicleRepository vehicleRepository;
    private final TripInfoMapper tripInfoMapper;

    @Transactional(readOnly = true)
    public List<TripDTO> getAllTrips() {
        return tripInfoRepository.findAll()
                .stream()
                .map(tripInfoMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TripDTO getTripById(Long id) {
        return tripInfoMapper.toDTO(findTrip(id));
    }

    @Transactional(readOnly = true)
    public List<TripDTO> getTripsByRouteId(Long routeId) {
        return tripInfoRepository.findAllByRoute_Id(routeId)
                .stream()
                .map(tripInfoMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TripDTO> getTripsByVehicleId(Long vehicleId) {
        return tripInfoRepository.findAllByVehicle_Id(vehicleId)
                .stream()
                .map(tripInfoMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TripDTO> searchTrips(TripSearchDTO dto) {
        String departureLocation = normalizeSearchText(dto.getDepartureLocation());
        String arrivalLocation = normalizeSearchText(dto.getArrivalLocation());

        return tripInfoRepository.searchTrips(
                dto.getRouteId(),
                dto.getVehicleId(),
                dto.getStatus(),
                departureLocation,
                arrivalLocation,
                dto.getDepartureFrom(),
                dto.getDepartureTo(),
                dto.getArrivalFrom(),
                dto.getArrivalTo())
                .stream()
                .map(tripInfoMapper::toDTO)
                .toList();
    }

    public TripDTO createTrip(TripCreateDTO dto) {
        TripInfo trip = tripInfoMapper.toEntity(dto);
        trip.setRoute(findRoute(dto.getRouteId()));
        trip.setVehicle(findVehicle(dto.getVehicleId()));
        if (dto.getStatus() != null) {
            trip.setStatus(dto.getStatus());
        }
        return tripInfoMapper.toDTO(tripInfoRepository.save(trip));
    }

    public TripDTO updateTrip(Long id, TripUpdateDTO dto) {
        TripInfo trip = findTrip(id);
        trip.setRoute(findRoute(dto.getRouteId()));
        trip.setVehicle(findVehicle(dto.getVehicleId()));
        trip.setDepartureTime(dto.getDepartureTime());
        trip.setArrivalTime(dto.getArrivalTime());
        trip.setTicketPrice(dto.getTicketPrice());
        trip.setStatus(dto.getStatus());
        return tripInfoMapper.toDTO(tripInfoRepository.save(trip));
    }

    public void deleteTrip(Long id) {
        tripInfoRepository.delete(findTrip(id));
    }

    private TripInfo findTrip(Long id) {
        return tripInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));
    }

    private Route findRoute(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found"));
    }

    private Vehicle findVehicle(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
    }

    private String normalizeSearchText(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
