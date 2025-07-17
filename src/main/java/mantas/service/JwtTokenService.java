import java.time.Instant;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Date;
import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class JwtTokenService {

    @ConfigProperty(name = "jwt.secret", defaultValue = "your-256-bit-secret")
    String jwtSecret;

    @ConfigProperty(name = "jwt.expiration", defaultValue = "3600") // 1 hour
    long jwtExpirationInSeconds;

    public String generateToken(User user) {
        Set<String> roles = user.getRoles().stream()
            .map(Role::name)
            .collect(Collectors.toSet());

        return Jwt.claims()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .groups(roles)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtExpirationInSeconds))
                .jws()
                .sign();
    }

    public long getExpirationTime() {
        return jwtExpirationInSeconds;
    }
}
