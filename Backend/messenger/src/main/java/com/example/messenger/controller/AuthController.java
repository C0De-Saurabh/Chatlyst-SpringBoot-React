package com.example.messenger.controller;

import com.example.messenger.entity.User;
import com.example.messenger.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Fetches the currently authenticated user details.
     * This works only after successful authentication.
     */
    @GetMapping("/user")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            throw new UnauthorizedException("User is not authenticated.");
        }
        return principal.getAttributes();
    }

    /**
     * Fetches the authenticated user from the database.
     */
    @GetMapping("/user/db")
    public User getUserFromDB(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            throw new UnauthorizedException("User is not authenticated.");
        }

        String email = principal.getAttribute("email");
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found in database."));
    }

    /**
     * Logs out the authenticated user.
     * Spring Security handles logout automatically.
     */
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate(); // Invalidate session
        response.sendRedirect("/"); // Redirect to home page after logout
    }

    /**
     * Custom Exception for Unauthorized Access.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }
}
