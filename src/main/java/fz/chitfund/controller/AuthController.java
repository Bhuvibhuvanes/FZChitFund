package fz.chitfund.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fz.chitfund.dto.ForgotPasswordRequest;
import fz.chitfund.dto.RegisterRequest;
import fz.chitfund.entity.Role;
import fz.chitfund.entity.Users;
import fz.chitfund.repository.RoleRepository;
import fz.chitfund.repository.UserRepository;
import fz.chitfund.security.JwtUtil;
import fz.chitfund.service.EmailService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.time.Duration;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private static final String RESET_TOKEN_EXPIRY = "1h";

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is required"));
        }

        Optional<Users> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No user found with this email"));
        }

        String resetToken = UUID.randomUUID().toString();
        user.get().setResetToken(resetToken);
        user.get().setResetTokenExpiry(new Date(System.currentTimeMillis() + Duration.parse(RESET_TOKEN_EXPIRY).toMillis()));
        userRepository.save(user.get());

        emailService.sendPasswordResetEmail(email, resetToken);
        return ResponseEntity.ok(Map.of("message", "Password reset instructions sent to your email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordRequest request) {
        if (request.getResetToken() == null || request.getResetToken().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Reset token is required"));
        }

        if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "New password is required"));
        }

        Optional<Users> user = userRepository.findByResetToken(request.getResetToken());
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid reset token"));
        }

        if (user.get().getResetTokenExpiry().before(new Date())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Reset token has expired"));
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.get().setPassword(encodedPassword);
        user.get().setResetToken(null);
        user.get().setResetTokenExpiry(null);
        userRepository.save(user.get());

        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    }

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		// Validate input
		if (request.getEmail() == null || request.getEmail().isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Email is required"));
		}

		if (request.getPassword() == null || request.getPassword().isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Password is required"));
		}

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
		}

		// Ensure at least one role is provided
		if (request.getRoles() == null || request.getRoles().isEmpty()) {
			request.setRoles(Set.of("ROLE_USER")); // Default to USER role
		}

		Users newuser = new Users();
		newuser.setUsername(request.getEmail());
		newuser.setEmail(request.getEmail());
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		newuser.setPassword(encodedPassword);

		Set<Role> roles = new HashSet<>();
		for (String roleName : request.getRoles()) {
			Role role = roleRepository.findByName(roleName)
				.orElseGet(() -> {
					Role newRole = new Role();
					newRole.setName(roleName);
					return roleRepository.save(newRole);
				});
			roles.add(role);
		}

		newuser.setRoles(roles);
		userRepository.save(newuser);

		// Send welcome email
		emailService.sendWelcomeEmail(newuser.getEmail());

		return ResponseEntity.ok(Map.of("message", "User registered successfully"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users request) {
		if (request.getEmail() == null || request.getPassword() == null) {
			return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required"));
		}

		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
			);
			String token = jwtUtil.generateToken(request.getEmail());
			return ResponseEntity.ok(Map.of("token", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
		}
	}
}
