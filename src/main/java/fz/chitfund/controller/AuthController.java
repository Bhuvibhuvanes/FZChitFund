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
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;

	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRrepository userRepository,
			PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("Username already exists");
		}
		Users newuser = new Users();
		newuser.setUsername(request.getUsername());
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		newuser.setPassword(encodedPassword);
		System.out.println("encode password" + encodedPassword);
		Set<Role> roles = new HashSet<>();
		for (String role : request.getRoles()) {
			Role r = roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Role not found"));
			roles.add(r);
		}
		newuser.setRoles(roles);
		userRepository.save(newuser);

		return ResponseEntity.ok("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Users request) {
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
			);
			String token = jwtUtil.generateToken(request.getUsername());
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Invalid username or password");
		}
	}

}