package eventlistener.service.notificaionuser;

import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
import eventlistener.repo.NotificationUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class NotificationUserService {

    NotificationUserRepo notificationUserRepo;


    NotificationUserMapper notificationUserMapper;



    public NotificationUserService(NotificationUserRepo notificationUserRepo, NotificationUserMapper notificationUserMapper) {
        this.notificationUserRepo = notificationUserRepo;
        this.notificationUserMapper = notificationUserMapper;

    }

    public List<NotificationUser> getAllUser() {
        return notificationUserRepo.findAll();
    }

    public NotificationUser addUser(NotificationUserDTO userDTO) {
        NotificationUser newUser = notificationUserMapper.mapNotificationUser(userDTO);
        return notificationUserRepo.save(newUser);
    }

    public NotificationUser getSingleUser(Long id) {
        Optional<NotificationUser> optionalUser = notificationUserRepo.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new NoSuchElementException("Cant find User with id " +id);
    }

    public NotificationUser editUser(NotificationUserEditDTO userToEdit) {
        Optional<NotificationUser> optionalUser = notificationUserRepo.findById(userToEdit.getId());
        if(optionalUser.isPresent()){
            return notificationUserRepo.save(notificationUserMapper.mapNotificationUser(userToEdit));
        }
        throw new NoSuchElementException("Can´t edit User with ID "+userToEdit.getId());
    }

    public List<NotificationUser> getUsersById(List<Long> userIds){
        List<NotificationUser> userList = new ArrayList<>();
        for (Long userId : userIds) {
            Optional<NotificationUser> optUser = notificationUserRepo.findById(userId);
            optUser.ifPresent(userList::add);
        }
        return userList;
    }

}
