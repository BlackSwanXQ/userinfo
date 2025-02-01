package com.example.userinfo.exceptions;

public class NotFoundException extends  RuntimeException {
    private final long id;
    public NotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
