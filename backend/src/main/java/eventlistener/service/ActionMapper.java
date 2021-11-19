package eventlistener.service;

import eventlistener.exception.ActionNoConfiguredException;
import eventlistener.model.Action;
import org.springframework.stereotype.Service;

@Service
public class ActionMapper {
    public Action mapAction(String actionString) {
        switch (actionString){
            case"MAIL":
                return Action.MAIL;
            case "SLACK":
                return Action.SLACK;
            default:
                throw new ActionNoConfiguredException(actionString+" is no valid Action.");
        }
    }
}
