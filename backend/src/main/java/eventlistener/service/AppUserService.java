package eventlistener.service;

import eventlistener.security.model.AppUserDTO;
import eventlistener.security.repo.AppUserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    AppUserRepo appUserRepo;

    private final BCryptPasswordEncoder passwordEncrypter = new BCryptPasswordEncoder();

    public AppUserService(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    public AppUserDTO addUser(AppUserDTO userToAdd) {
        return appUserRepo.save(AppUserDTO.builder()
                .username(userToAdd.getUsername())
                .password(passwordEncrypter.encode(userToAdd.getPassword()))
                .build());
    }
}
