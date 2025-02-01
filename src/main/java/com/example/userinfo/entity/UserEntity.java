package com.example.userinfo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymicName;
    private LocalDate birthday;
    private String email;
    private String phone;

    public UserEntity(Long id,
                      String firstName,
                      String lastName,
                      String patronymicName,
                      LocalDate birthday, String email,
                      String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
    }

    public UserEntity(){}

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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity users = (UserEntity) o;
        return Objects.equals(id, users.id) && Objects.equals(firstName, users.firstName) && Objects.equals(lastName, users.lastName) && Objects.equals(patronymicName, users.patronymicName) && Objects.equals(birthday, users.birthday) && Objects.equals(email, users.email) && Objects.equals(phone, users.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patronymicName, birthday, email, phone);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymicName='" + patronymicName + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
