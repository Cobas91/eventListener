/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

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
