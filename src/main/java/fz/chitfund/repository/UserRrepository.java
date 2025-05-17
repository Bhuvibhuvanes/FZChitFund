package fz.chitfund.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fz.chitfund.entity.Users;

@Repository
public interface UserRrepository extends JpaRepository<Users, Long>{

	Optional<Users> findByUsername(String username);
	Optional<Users> findByEmail(String email);

}
