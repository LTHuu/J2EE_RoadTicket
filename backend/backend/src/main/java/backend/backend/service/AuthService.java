
package backend.backend.service;

import backend.backend.dto.auth.LoginRequest;
import backend.backend.dto.auth.LoginResponse;
import backend.backend.dto.auth.RegisterRequest;
import backend.backend.entity.User;
import backend.backend.enums.AuthProvider;
import backend.backend.enums.UserRole;
import backend.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public void register(RegisterRequest request) {
        String email = request.getEmail()
                .trim()
                .toLowerCase();

        String username = request.getUsername()
                .trim();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Email này đã được sử dụng");
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Username này đã được sử dụng");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(
                passwordEncoder.encode(request.getPassword()));

        // Các giá trị này do backend quyết định
        user.setRole(UserRole.CUSTOMER);
        user.setProvider(AuthProvider.LOCAL);
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);

        emailVerificationService.createAndSend(savedUser);
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            request.getPassword()));
        } catch (DisabledException e) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Vui lòng xác thực email trước khi đăng nhập");
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Email hoặc mật khẩu không đúng");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Email hoặc mật khẩu không đúng"));

        String accessToken = jwtService.generateToken(user);

        return new LoginResponse(
                accessToken,
                "Bearer",
                JwtService.ACCESS_TOKEN_TTL_SECONDS,
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().name());
    }

}
