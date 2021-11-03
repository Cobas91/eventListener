package eventlistener.service.notificaionuser;

import eventlistener.exception.EventNotFoundException;
import eventlistener.model.notificationUser.NotificationUser;
import eventlistener.model.notificationUser.NotificationUserDTO;
import eventlistener.repo.EventRepo;
import eventlistener.repo.NotificationUserRepo;
import eventlistener.service.event.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NotificationUserService {

    NotificationUserRepo notificationUserRepo;

    EventRepo eventRepo;

    NotificationUserMapper notificationUserMapper;

    EventService eventService;

    public NotificationUserService(NotificationUserRepo notificationUserRepo,EventRepo eventRepo, NotificationUserMapper notificationUserMapper, EventService eventService) {
        this.notificationUserRepo = notificationUserRepo;
        this.eventRepo = eventRepo;
        this.notificationUserMapper = notificationUserMapper;
        this.eventService = eventService;
    }

    public List<NotificationUser> getAllUser() {
        return notificationUserRepo.findAll();
    }

    public NotificationUser addUser(NotificationUserDTO userDTO) {
        if(eventService.eventsExist(userDTO.getListenEvents())){
            NotificationUser newUser = notificationUserMapper.mapDTO(userDTO);
            NotificationUser userWithID = notificationUserRepo.save(newUser);
            eventService.addUserToEvents(userDTO.getListenEvents(), userWithID);
            return userWithID;
        }else{
            throw new EventNotFoundException("Can not add User: "+userDTO.getName()+" events are not existing.");
        }
    }


    public NotificationUser getSingleUser(String id) {
        Optional<NotificationUser> optionalUser = notificationUserRepo.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new NoSuchElementException("Cant find User with id " +id);
    }
}
