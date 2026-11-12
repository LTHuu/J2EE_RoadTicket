package backend.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import backend.backend.dto.vehicle.VehicleCreateDTO;
import backend.backend.dto.vehicle.VehicleDTO;
import backend.backend.entity.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "busCompany", ignore = true)
    Vehicle toEntity(VehicleCreateDTO dto);

    @Mapping(source = "busCompany.id", target = "busCompanyId")
    @Mapping(source = "busCompany.name", target = "busCompanyName")
    VehicleDTO toDTO(Vehicle vehicle);
}
