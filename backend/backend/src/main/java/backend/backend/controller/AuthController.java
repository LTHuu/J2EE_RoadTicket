
package backend.backend.controller;

import backend.backend.dto.auth.LoginRequest;
import backend.backend.dto.auth.LoginResponse;
import backend.backend.dto.auth.RegisterRequest;
import backend.backend.dto.auth.CurrentUserResponse;
import backend.backend.service.AuthService;
import backend.backend.service.EmailVerificationService;
import backend.backend.service.GoogleAuthService;
import backend.backend.dto.GoogleRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final GoogleAuthService googleAuthService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message",
                        "Đăng ký thành công. Vui lòng xác thực email."));
    }

    private final EmailVerificationService emailVerificationService;

    @GetMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(
            @RequestParam String token) {

        emailVerificationService.verifyEmail(token);

        return ResponseEntity.ok(Map.of(
                "message",
                "Xác thực email thành công. Bạn có thể đăng nhập."));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(
            @Valid @RequestBody GoogleRegisterDTO request) {

        return ResponseEntity.ok(
                googleAuthService.login(request.getCredential()));
    }

    @GetMapping("/me")
    public CurrentUserResponse currentUser(
            @AuthenticationPrincipal Jwt jwt) {

        return new CurrentUserResponse(
                jwt.getClaim("userId"),
                jwt.getSubject(),
                jwt.getClaim("username"),
                jwt.getClaim("role"));
    }

}
