package fz.chitfund.security;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fz.chitfund.entity.Users;
import fz.chitfund.repository.UserRrepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import fz.chitfund.entity.Role;

@Component
public class JwtUtil {
	private static SecretKey seckey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private int jwtExtensionms = 86400000;
	
	@Autowired
	private UserRrepository usrepository;

	public JwtUtil(UserRrepository usrepository) {
		this.usrepository = usrepository;
	}

	public String generateToken(String username) {
		Optional<Users> user = usrepository.findByUsername(username);
		Set<Role> roles = user.get().getRoles();
		return Jwts.builder().setSubject(username)
				.claim("roles", roles.stream().map(role -> role.getName()).collect(Collectors.joining(",")))
				.setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + jwtExtensionms)).signWith(seckey)
				.compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(seckey).build().parseClaimsJws(token).getBody().getSubject();
	}

	public Set<String> extractRoles(String token) {
		String rolestring = Jwts.parserBuilder().setSigningKey(seckey).build().parseClaimsJws(token).getBody()
				.get("roles", String.class);
		return Set.of(rolestring.split(","));
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(seckey).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
