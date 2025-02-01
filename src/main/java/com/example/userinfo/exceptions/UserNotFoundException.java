package com.example.userinfo.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(long id) {
        super(id);
    }
    @Override
    public String getMessage() {
        return "User with id = %d not found".formatted(getId());
    }
}
