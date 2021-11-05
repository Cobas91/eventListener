package eventlistener.service.notificaionuser;

import eventlistener.exception.EventNotFoundException;
import eventlistener.model.event.Event;
import eventlistener.model.notificationuser.NotificationUser;
import eventlistener.model.notificationuser.NotificationUserDTO;
import eventlistener.model.notificationuser.NotificationUserEditDTO;
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


    public NotificationUserDTO getSingleUser(String id) {
        Optional<NotificationUser> optionalUser = notificationUserRepo.findById(id);
        if(optionalUser.isPresent()){
            return notificationUserMapper.mapUser(optionalUser.get());
        }
        throw new NoSuchElementException("Cant find User with id " +id);
    }
}
