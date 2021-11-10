package eventlistener.security.repo;

import eventlistener.security.model.AppUserDTO;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends PagingAndSortingRepository<AppUserDTO, String> {
    Optional<AppUserDTO> findByUsername(String username);
}
