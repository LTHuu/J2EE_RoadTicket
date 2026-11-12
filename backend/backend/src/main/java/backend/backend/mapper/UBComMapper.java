package backend.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import backend.backend.dto.UBCom.UBComAddDTO;
import backend.backend.dto.UBCom.UBComDTO;
import backend.backend.entity.User_BusCom;

@Mapper(componentModel = "spring")
public interface UBComMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "busCompany", ignore = true)
    User_BusCom toEntity(UBComAddDTO dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "busCompanyId", source = "busCompany.id")
    @Mapping(target = "busCompanyName", source = "busCompany.name")
    UBComDTO toDTO(User_BusCom entity);
}
