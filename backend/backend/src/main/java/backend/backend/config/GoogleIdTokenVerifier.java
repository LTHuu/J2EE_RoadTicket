package backend.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@RequiredArgsConstructor
public class GoogleIdTokenVerifier {

    private final JwtDecoder decoder;

    public Jwt verify(String credential) {
        return decoder.decode(credential);
    }
}
