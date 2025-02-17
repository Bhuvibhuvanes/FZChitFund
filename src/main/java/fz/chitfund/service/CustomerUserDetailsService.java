package fz.chitfund.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fz.chitfund.entity.Users;
import fz.chitfund.repository.UserRrepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	private UserRrepository userrepository;

	public CustomerUserDetailsService(UserRrepository userrepository) {
		super();
		this.userrepository = userrepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users us = userrepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("user name not found" + username));
		return new org.springframework.security.core.userdetails.User(us.getUsername(), us.getPassword(),
				us.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toList()));

	}

}
