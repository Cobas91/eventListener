package eventlistener.service;

import eventlistener.model.NotificationUser;
import eventlistener.repo.NotificationUserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public NotificationUser getSingleUser(String id) {
        Optional<NotificationUser> optionalUser = notificationUserRepo.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new NoSuchElementException("Cant find User with id " +id);
    }
}
