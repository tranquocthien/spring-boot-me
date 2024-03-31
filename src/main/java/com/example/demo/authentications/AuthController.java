package com.example.demo.authentications;

import com.example.demo.authentications.dto.request.UserLoginDto;
import com.example.demo.authentications.dto.response.AuthResponse;
import com.example.demo.authentications.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        System.out.println("abcxxx-" + request);
        return ResponseEntity.ok(authService.createUser(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
           @Valid @RequestBody UserLoginDto payload
    ) {
        System.out.println("abcxxx" + payload);
        return ResponseEntity.ok(authService.getAuthenticatedUser(payload));
    }
}