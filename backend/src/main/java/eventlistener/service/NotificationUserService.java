package eventlistener.service;

import eventlistener.model.NotificationUser;
import eventlistener.repo.NotificationUserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationUserService {

    NotificationUserRepo notificationUserRepo;

    public NotificationUserService(NotificationUserRepo notificationUserRepo) {
        this.notificationUserRepo = notificationUserRepo;
    }

    public List<NotificationUser> getAllUser() {
        return notificationUserRepo.findAll();
    }

    public NotificationUser addUser(NotificationUser userToAdd) {
        return notificationUserRepo.save(userToAdd);
    }
}
