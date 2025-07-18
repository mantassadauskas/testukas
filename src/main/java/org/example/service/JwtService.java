package org.example.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.entity.User;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateToken(User user) {
        return Jwt.issuer(issuer)
                .upn(user.getUsername())
                .subject(user.getUsername())
                .groups(Set.of(user.getRole().name()))
                .claim("userId", user.getId())
                .claim("fullName", user.getFullName())
                .expiresIn(Duration.ofHours(24))
                .sign();
    }
}