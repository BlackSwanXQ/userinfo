package com.example.userinfo.exceptions;

public class PhotoNotFountException extends NotFoundException{
    public PhotoNotFountException(long id) {
        super(id);
    }
    @Override
    public String getMessage() {
        return "User with id = %d not found".formatted(getId());
    }
}
