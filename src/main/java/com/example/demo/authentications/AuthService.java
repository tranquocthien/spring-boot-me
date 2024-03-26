package com.example.demo.authentications;

import com.example.demo.authentications.dto.request.UserLoginDto;
import com.example.demo.authentications.dto.response.AuthResponse;
import com.example.demo.authentications.dto.RegisterRequest;
import com.example.demo.config.JwtService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.users.User;
import com.example.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse createUser(RegisterRequest request) {
        var cryptPassword = generateCryptPassword(request.getPassword());
        var user = User.builder().email(request.getEmail()).name(request.getName()).hash(cryptPassword.hash).role(request.getRole()).build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    public AuthResponse getAuthenticatedUser(UserLoginDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("error");
        }
        User existingUser = user.get();
        var jwtToken = jwtService.generateToken(existingUser);
        var refreshToken = jwtService.generateRefreshToken(existingUser);
        return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    public static HashAndSalt generateCryptPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);

        String salt = BCrypt.gensalt(12, random);
        String hashedPassword = hashPassword(password, salt);

        return new HashAndSalt(hashedPassword, salt);
    }

    public static String hashPassword(String plainPassword, String salt) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(plainPassword + salt);
    }

    // Refactored to use a record for HashAndSalt
    public record HashAndSalt(String hash, String salt) {
    }
}