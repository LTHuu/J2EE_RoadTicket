package backend.backend.mapper;

import backend.backend.dto.auth.CurrentUserResponse;
import backend.backend.dto.auth.LoginResponse;
import backend.backend.dto.auth.RegisterRequest;
import backend.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.oauth2.jwt.Jwt;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    User toGoogleEntity(String email, String username);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    User toAdminEntity(String email, String username);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "tokenType", source = "tokenType")
    @Mapping(target = "expiresIn", source = "expiresIn")
    LoginResponse toLoginResponse(
            User user,
            String accessToken,
            String tokenType,
            long expiresIn);

    @Mapping(
            target = "userId",
            expression = "java(((Number) jwt.getClaim(\"userId\")).longValue())")
    @Mapping(target = "email", source = "subject")
    @Mapping(target = "username", expression = "java(jwt.getClaimAsString(\"username\"))")
    @Mapping(target = "role", expression = "java(jwt.getClaimAsString(\"role\"))")
    CurrentUserResponse toCurrentUserResponse(Jwt jwt);
}