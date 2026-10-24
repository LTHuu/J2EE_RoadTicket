
package backend.backend.dto.auth;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        Long userId,
        String email,
        String username,
        String role) {
}
