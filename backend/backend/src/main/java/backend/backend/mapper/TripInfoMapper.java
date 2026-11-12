package backend.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import backend.backend.dto.trip.TripCreateDTO;
import backend.backend.dto.trip.TripDTO;
import backend.backend.entity.TripInfo;

@Mapper(componentModel = "spring")
public interface TripInfoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "vehicle", ignore = true)
    @Mapping(target = "status", ignore = true)
    TripInfo toEntity(TripCreateDTO dto);

    @Mapping(source = "route.id", target = "routeId")
    @Mapping(source = "route.departureLocation", target = "departureLocation")
    @Mapping(source = "route.arrivalLocation", target = "arrivalLocation")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.name", target = "vehicleName")
    @Mapping(source = "vehicle.licensePlate", target = "licensePlate")
    TripDTO toDTO(TripInfo trip);
}
