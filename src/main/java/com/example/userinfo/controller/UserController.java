package com.example.userinfo.controller;

import com.example.userinfo.dto.ContactsDTO;
import com.example.userinfo.dto.DetailsDTO;
import com.example.userinfo.entity.UserEntity;
import com.example.userinfo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Создает пользователя.
     */
    @PostMapping
    public UserEntity create(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    /**
     * Получает пользователя.
     */
    @GetMapping("/{id}")
    public UserEntity get(@PathVariable long id) {
        return userService.getUser(id);
    }

    /**
     * Обновляет всю информацию о пользователе.
     */
    @PutMapping("/{id}")
    public UserEntity update(@PathVariable long id,
                             @RequestBody UserEntity user) {
        return userService.updateUser(id, user);
    }

    /**
     * Обновляет информацию о контактах пользователя.
     */
    @PatchMapping(value = "/contacts/{id}")
    public UserEntity updateContactsInfo(@PathVariable long id,
                                      @RequestBody ContactsDTO contacts) {
        return userService.updateContacts(id, contacts);
    }

    /**
     * Обновляет информацию о Ф.И.О. пользователя.
     */
    @PatchMapping(value = "/details/{id}")
    public UserEntity updateDetailsInfo(@PathVariable long id,
                                         @RequestBody DetailsDTO details) {
        return userService.updateDetails(id, details);
    }

    /**
     * Удаляет пользователя.
     */
    @DeleteMapping("/{id}")
    public UserEntity delete(@PathVariable long id) {
        return userService.removeUser(id);
    }

}
