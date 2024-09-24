package com.bdool.chatservice.exception;

public class ParticipantIdNotFoundException extends RuntimeException {
    public ParticipantIdNotFoundException(String message) {
        super(message);
    }
}
