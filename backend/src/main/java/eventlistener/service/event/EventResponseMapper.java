package eventlistener.service.event;

import eventlistener.model.event.Event;
import eventlistener.model.event.ResponseEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventResponseMapper {

    public List<ResponseEvent> mapResponseEvents(List<Event> events){
        List<ResponseEvent> mappedEvents = new ArrayList<>();
        for (Event event : events) {
            mappedEvents.add(ResponseEvent.builder().name(event.getEventName()).id(event.getId()).actions(event.getActions()).build());
        }
        return mappedEvents;
    }

}
