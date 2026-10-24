package backend.backend;

import backend.backend.entity.User;
import backend.backend.enums.AuthProvider;
import backend.backend.enums.UserRole;
import backend.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminUsername;
    private final String adminPassword;

    public DataInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.email:}") String adminEmail,
            @Value("${app.admin.username:}") String adminUsername,
            @Value("${app.admin.password:}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (adminEmail.isBlank() && adminUsername.isBlank()
                && adminPassword.isBlank()) {
            log.info("Admin seed skipped: admin credentials are not configured.");
            return;
        }

        validateConfiguration();

        String normalizedEmail = adminEmail.trim().toLowerCase(Locale.ROOT);
        String username = adminUsername.trim();
        User existingUser = userRepository.findByEmail(normalizedEmail)
                .orElse(null);

        if (existingUser != null) {
            if (existingUser.getRole() != UserRole.ADMIN) {
                throw new IllegalStateException(
                        "Configured admin email already belongs to a non-admin user.");
            }

            log.info("Configured admin account already exists; leaving it unchanged.");
            return;
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException(
                    "Configured admin username is already in use.");
        }

        User admin = new User();
        admin.setEmail(normalizedEmail);
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(UserRole.ADMIN);
        admin.setProvider(AuthProvider.LOCAL);
        admin.setEmailVerified(true);

        userRepository.save(admin);
        log.info("Initial admin account created.");
    }

    private void validateConfiguration() {
        if (adminEmail.isBlank() || adminUsername.isBlank()
                || adminPassword.isBlank()) {
            throw new IllegalStateException(
                    "ADMIN_EMAIL, ADMIN_USERNAME, and ADMIN_PASSWORD must all be configured.");
        }

        String username = adminUsername.trim();
        if (username.length() < 4 || username.length() > 50) {
            throw new IllegalStateException(
                    "ADMIN_USERNAME must contain between 4 and 50 characters.");
        }

        if (adminPassword.length() < 8 || adminPassword.length() > 72) {
            throw new IllegalStateException(
                    "ADMIN_PASSWORD must contain between 8 and 72 characters.");
        }
    }
}
