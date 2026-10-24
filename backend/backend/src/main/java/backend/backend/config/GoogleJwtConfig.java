package backend.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.util.List;

@Configuration
public class GoogleJwtConfig {

    private static final String GOOGLE_JWK_SET_URI =
            "https://www.googleapis.com/oauth2/v3/certs";

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier(
            @Value("${app.google.client-id:}") String clientId) {

        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withJwkSetUri(GOOGLE_JWK_SET_URI)
                .jwsAlgorithms(algorithms -> algorithms.add(
                        SignatureAlgorithm.RS256))
                .build();

        OAuth2TokenValidator<Jwt> issuerValidator = jwt -> {
            String issuer = jwt.getIssuer() == null
                    ? ""
                    : jwt.getIssuer().toString();
            return List.of("https://accounts.google.com", "accounts.google.com")
                    .contains(issuer)
                    ? OAuth2TokenValidatorResult.success()
                    : OAuth2TokenValidatorResult.failure(
                            new OAuth2Error("invalid_token",
                                    "Invalid Google token issuer", null));
        };

        OAuth2TokenValidator<Jwt> audienceValidator = jwt ->
                !clientId.isBlank() && jwt.getAudience().contains(clientId)
                        ? OAuth2TokenValidatorResult.success()
                        : OAuth2TokenValidatorResult.failure(
                                new OAuth2Error("invalid_token",
                                        "Invalid Google token audience", null));

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefault(),
                issuerValidator,
                audienceValidator));
        return new GoogleIdTokenVerifier(decoder);
    }
}
