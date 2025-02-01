package com.example.userinfo.dto;

import java.util.Date;

public class DetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymicName;

    public DetailsDTO(Long id, String firstName, String lastName, String patronymicName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }
}
