package com.example.messenger.controller;
import com.example.messenger.model.User;
import com.example.messenger.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Fetches the currently authenticated user details.
     * Only accessible if the user is authenticated.
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
     * If the user does not exist, creates a new user in the database.
     */
    @GetMapping("/user/db")
    public User getUserFromDB(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            throw new UnauthorizedException("User is not authenticated.");
        }

        String email = principal.getAttribute("email");
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(principal.getAttribute("name"));
                    return userRepository.save(newUser);
                });
    }

    /**
     * Local logout - Clears session & redirects user to Google login.
     */
    @PostMapping("/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            request.getSession().invalidate(); // Invalidate session
        }
        SecurityContextHolder.clearContext();

        // Redirect user to Google login page (so they can switch accounts)
        return new RedirectView("/oauth2/authorization/google");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }
}