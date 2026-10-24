package backend.backend.service;

import backend.backend.dto.auth.LoginResponse;
import backend.backend.config.GoogleIdTokenVerifier;
import backend.backend.entity.User;
import backend.backend.enums.AuthProvider;
import backend.backend.enums.UserRole;
import backend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${app.google.client-id:}")
    private String googleClientId;

    @Transactional
    public LoginResponse login(String credential) {
        System.out.println(
                "Google Client ID configured: "
                        + (googleClientId != null && !googleClientId.isBlank()));

        if (googleClientId == null || googleClientId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Đăng nhập Google chưa được cấu hình");
        }
        Jwt googleToken;
        try {
            googleToken = googleIdTokenVerifier.verify(credential);
        } catch (JwtException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Thông tin xác thực Google không hợp lệ");
        }

        String email = googleToken.getClaimAsString("email");
        String subject = googleToken.getSubject();
        Object verifiedClaim = googleToken.getClaims().get("email_verified");
        boolean emailVerified = Boolean.TRUE.equals(verifiedClaim)
                || "true".equals(verifiedClaim);

        if (!emailVerified || email == null || email.isBlank()
                || subject == null || subject.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Tài khoản Google cần có email đã xác thực");
        }

        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
        User user = userRepository.findByEmail(normalizedEmail)
                .map(existingUser -> {
                    if (existingUser.getProvider() != AuthProvider.GOOGLE) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Email này đã đăng ký bằng mật khẩu. "
                                        + "Vui lòng đăng nhập bằng email và mật khẩu.");
                    }
                    return existingUser;
                })
                .orElseGet(() -> createGoogleUser(normalizedEmail, subject));

        return new LoginResponse(
                jwtService.generateToken(user),
                "Bearer",
                JwtService.ACCESS_TOKEN_TTL_SECONDS,
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().name());
    }

    private User createGoogleUser(String email, String subject) {
        User user = new User();
        user.setEmail(email);
        user.setUsername(createUsername(subject));
        user.setRole(UserRole.CUSTOMER);
        user.setProvider(AuthProvider.GOOGLE);
        user.setEmailVerified(true);
        return userRepository.save(user);
    }

    private String createUsername(String subject) {
        String base = "google_" + subject.replaceAll("[^A-Za-z0-9_-]", "");
        if (base.length() > 50) {
            base = base.substring(0, 50);
        }

        String candidate = base;
        int suffix = 1;
        while (userRepository.existsByUsername(candidate)) {
            String suffixText = "_" + suffix++;
            candidate = base.substring(
                    0, Math.min(base.length(), 50 - suffixText.length()))
                    + suffixText;
        }
        return candidate;
    }
}
