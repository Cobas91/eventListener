/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.exception;


public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String message, Exception e) {
        super(message, e);
    }
    public EventNotFoundException(String message) {
        super(message);
    }
}
