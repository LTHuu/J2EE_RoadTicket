package backend.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import backend.backend.dto.buscompany.BusCompanyCreateDTO;
import backend.backend.dto.buscompany.BusCompanyDTO;
import backend.backend.dto.buscompany.BusCompanyUpdateDTO;
import backend.backend.entity.BusCompany;

@Mapper(componentModel = "spring")
public interface BusCompanyMapper {

    @Mapping(target = "id", ignore = true)
    BusCompany toEntity(BusCompanyCreateDTO dto);

    BusCompanyDTO toDTO(BusCompany busCompany);

    @Mapping(target = "id", ignore = true)
    void updateEntity(BusCompanyUpdateDTO dto, @MappingTarget BusCompany busCompany);
}
