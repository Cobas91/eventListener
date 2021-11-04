package eventlistener.service;

import eventlistener.security.model.AppUserDTO;
import eventlistener.security.repo.AppUserRepo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    private final AppUserRepo appUserRepo = mock(AppUserRepo.class);

    private final AppUserService appUserService = new AppUserService(appUserRepo);

    @Test
    @DisplayName("Should add a AppUser to the DB")
    void testAddUser() {
        //GIVEN
        AppUserDTO userToAdd = AppUserDTO.builder()
                .username("TestUser")
                .password("Password123")
                .build();
        //WHEN
        when(appUserRepo.save(userToAdd)).thenReturn(userToAdd);

        AppUserDTO expected = appUserService.addUser(userToAdd);
        userToAdd.setPassword(expected.getPassword());
        //THEN
        verify(appUserRepo).save(userToAdd);
        assertThat(expected, is(userToAdd));

    }
}