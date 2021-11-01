package eventlistener.service;

import eventlistener.security.model.AppUser;
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

    public AppUser addUser(AppUser userToAdd) {
        return appUserRepo.save(AppUser.builder()
                .username(userToAdd.getUsername())
                .password(passwordEncrypter.encode(userToAdd.getPassword()))
                .build());
    }
}
