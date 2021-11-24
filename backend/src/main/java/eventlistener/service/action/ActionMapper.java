package eventlistener.service.action;

import eventlistener.exception.ActionNotConfiguredException;
import eventlistener.model.Action;
import org.springframework.stereotype.Service;


@Service
public class ActionMapper {
    public Action mapAction(String actionString) {
        String action = actionString.toLowerCase();
        switch (action){
            case"mail":
                return Action.MAIL;
            case "slack":
                return Action.SLACK;
            default:
                throw new ActionNotConfiguredException(actionString+" is no valid Action.");
        }
    }
}
