package fz.chitfund.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fz.chitfund.dto.RegisterRequest;
import fz.chitfund.entity.Role;
import fz.chitfund.entity.Users;
import fz.chitfund.repository.RoleRepository;
import fz.chitfund.repository.UserRrepository;
import fz.chitfund.security.JwtUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;

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
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserRrepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRrepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		// Validate input
		if (request.getUsername() == null || request.getUsername().isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Username is required"));
		}

		if (request.getPassword() == null || request.getPassword().isEmpty()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Password is required"));
		}

		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
		}

		// Ensure at least one role is provided
		if (request.getRoles() == null || request.getRoles().isEmpty()) {
			request.setRoles(Set.of("ROLE_USER")); // Default to USER role
		}

		Users newuser = new Users();
		newuser.setUsername(request.getUsername());
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

		return ResponseEntity.ok(Map.of("message", "User registered successfully"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users request) {
		if (request.getUsername() == null || request.getPassword() == null) {
			return ResponseEntity.badRequest().body(Map.of("error", "Username and password are required"));
		}

		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
			);
			String token = jwtUtil.generateToken(request.getUsername());
			return ResponseEntity.ok(Map.of("token", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
		}
	}

}