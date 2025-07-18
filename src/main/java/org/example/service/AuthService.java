package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    JwtService jwtService;

    public Optional<String> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean match = BCrypt.checkpw(password, user.getPassword());

            if (match) {
                String token = jwtService.generateToken(user);
                return Optional.of(token);
            }
        } else {
            System.out.println("User not found for username: " + username);
        }

        return Optional.empty();
    }

    public User createUser(String username, String password, String fullName) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashedPassword, fullName);
        userRepository.persist(user);
        return user;
    }
}

