package org.example.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.service.AuthService;

import java.util.Optional;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    AuthService authService;

    @Inject
    UserRepository userRepository;

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        Optional<String> tokenOpt = authService.authenticate(request.username, request.password);
        System.out.println("Username input: " + request.username + request.password);

        if (tokenOpt.isPresent()) {
            // Get user info for response
            User user = userRepository.findByUsername(request.username).orElseThrow();
            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getRole().name()
            );

            LoginResponse response = new LoginResponse(
                    true,
                    "Login successful",
                    tokenOpt.get(),
                    userInfo
            );

            return Response.ok(response).build();
        } else {
            LoginResponse response = new LoginResponse(false, "Invalid username or password");
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
    }

    @POST
    @Path("/register")
    @Transactional
    public Response register(RegisterRequest request) {
        try {
            // Check if user already exists
            if (userRepository.findByUsername(request.username).isPresent()) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(new LoginResponse(false, "Username already exists"))
                        .build();
            }

            User user = authService.createUser(request.username, request.password, request.fullName);

            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getRole().name()
            );

            LoginResponse response = new LoginResponse(
                    true,
                    "User created successfully",
                    null,
                    userInfo
            );

            return Response.status(Response.Status.CREATED).entity(response).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new LoginResponse(false, "Registration failed"))
                    .build();
        }
    }

    public static class RegisterRequest {
        public String username;
        public String password;
        public String fullName;
    }
}