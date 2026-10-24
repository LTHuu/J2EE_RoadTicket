
package backend.backend.service;

import backend.backend.entity.EmailVerificationToken;
import backend.backend.entity.User;
import backend.backend.repository.EmailVerificationTokenRepository;
import backend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public void createAndSend(User user) {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);

        String rawToken = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        EmailVerificationToken token = new EmailVerificationToken();

        token.setUser(user);
        token.setTokenHash(hashToken(rawToken));
        token.setExpiresAt(
                Instant.now().plus(15, ChronoUnit.MINUTES));

        tokenRepository.save(token);

        // Chỉ gửi token gốc qua email.
        // Database chỉ lưu bản hash.
        emailService.sendVerificationEmail(
                user.getEmail(),
                rawToken);
    }

    @Transactional
    public void verifyEmail(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Token không hợp lệ");
        }

        EmailVerificationToken token = tokenRepository.findByTokenHash(
                hashToken(rawToken)).orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Token không hợp lệ hoặc không tồn tại"));

        if (token.getUsedAt() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Token đã được sử dụng");
        }

        if (Instant.now().isAfter(token.getExpiresAt())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Token đã hết hạn");
        }

        User user = token.getUser();
        user.setEmailVerified(true);
        token.setUsedAt(Instant.now());

        userRepository.save(user);
        tokenRepository.save(token);
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    rawToken.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Không thể tạo hash cho token", e);
        }
    }
}
