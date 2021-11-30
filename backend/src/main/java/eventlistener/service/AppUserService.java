/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

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
        userToAdd.setPassword(passwordEncrypter.encode(userToAdd.getPassword()));
        return appUserRepo.save(userToAdd);
    }

}
