package backend.backend.dto.auth;

public record CurrentUserResponse(
        Long userId,
        String email,
        String username,
        String role) {
}
