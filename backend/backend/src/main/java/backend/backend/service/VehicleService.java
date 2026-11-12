package backend.backend.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import backend.backend.dto.vehicle.VehicleCreateDTO;
import backend.backend.dto.vehicle.VehicleDTO;
import backend.backend.dto.vehicle.VehicleSearchDTO;
import backend.backend.dto.vehicle.VehicleUpdateDTO;
import backend.backend.entity.BusCompany;
import backend.backend.entity.Vehicle;
import backend.backend.mapper.VehicleMapper;
import backend.backend.repository.BusCompanyRepository;
import backend.backend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final BusCompanyRepository busCompanyRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional(readOnly = true)
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public VehicleDTO getVehicleById(Long id) {
        return vehicleMapper.toDTO(findVehicle(id));
    }

    @Transactional(readOnly = true)
    public VehicleDTO getVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate)
                .map(vehicleMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> getVehiclesByBusCompanyId(Long busCompanyId) {
        return vehicleRepository.findAllByBusCompany_Id(busCompanyId)
                .stream()
                .map(vehicleMapper::toDTO)
                .toList();
    }

    public VehicleDTO addVehicle(VehicleCreateDTO dto) {
        Vehicle vehicle = vehicleMapper.toEntity(dto);
        vehicle.setBusCompany(findBusCompany(dto.getBusCompanyId()));
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(savedVehicle);
    }

    public VehicleDTO updateVehicle(Long id, VehicleUpdateDTO dto) {
        Vehicle vehicle = findVehicle(id);
        vehicle.setBusCompany(findBusCompany(dto.getBusCompanyId()));
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setName(dto.getName());
        vehicle.setType(dto.getType());
        vehicle.setSeatCapacity(dto.getSeatCapacity());
        vehicle.setActive(dto.isActive());
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDTO(updatedVehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.delete(findVehicle(id));
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> searchVehicles(VehicleSearchDTO dto) {
        String text = dto.getText();
        if (text != null && text.isBlank()) {
            text = null;
        }

        return vehicleRepository.searchVehicles(text, dto.getType(), dto.getActive())
                .stream()
                .map(vehicleMapper::toDTO)
                .toList();
    }

    private Vehicle findVehicle(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
    }

    private BusCompany findBusCompany(Long id) {
        return busCompanyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus company not found"));
    }
}
