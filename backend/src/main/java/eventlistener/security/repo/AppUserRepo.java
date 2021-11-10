package eventlistener.security.repo;

import eventlistener.security.model.AppUserDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUserDTO, String> {
    Optional<AppUserDTO> findByUsername(String username);
}
