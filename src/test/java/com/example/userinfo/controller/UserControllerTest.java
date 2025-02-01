package com.example.userinfo.controller;

import com.example.userinfo.entity.UserEntity;
import com.example.userinfo.repository.UserRepository;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.h2.engine.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private final Faker faker = new Faker();
    private final List<UserEntity> users = new ArrayList<>(10);

    @BeforeEach
    void beforeEach() {
        createUsers();
    }

    private void createUsers() {
        users.clear();
        Stream.of(users)
                .forEach(users -> users.addAll(
                        Stream.generate(() -> {
                                    UserEntity user = new UserEntity();
                                    user.setFirstName(faker.name().firstName());
                                    user.setLastName(faker.name().lastName());
                                    user.setPatronymicName(faker.name().nameWithMiddle());
                                    user.setBirthday(LocalDate.ofEpochDay(faker.random().nextInt(20, 50)));
                                    user.setPhone(faker.phoneNumber().cellPhone());
                                    user.setEmail(faker.internet().emailAddress());
                                    return user;
                                })
                                .limit(10)
                                .collect(Collectors.toList())));

        userRepository.saveAll(users);

    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    private String buildUrl(String urlStartsWithSlash) {
        return "http://localhost:%d%s".formatted(port, urlStartsWithSlash);
    }

    @Test
    void createUserTest() {

        UserEntity user = new UserEntity();
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setPatronymicName(faker.name().nameWithMiddle());
        user.setBirthday(LocalDate.ofEpochDay(faker.random().nextInt(20, 50)));
        user.setPhone(faker.phoneNumber().cellPhone());
        user.setEmail(faker.internet().emailAddress());

        createUser(user);
    }

    private void createUser(UserEntity user) {
        ResponseEntity<UserEntity> responseEntity = restTemplate.postForEntity(buildUrl("/userinfo"),
                user,
                UserEntity.class
        );

        UserEntity created = responseEntity.getBody();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(created).isNotNull();
        Assertions.assertThat(created).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(user);
        Assertions.assertThat(created.getId()).isNotNull();

        Optional<UserEntity> fromDB = userRepository.findById(created.getId());
        Assertions.assertThat(fromDB).isPresent();
        Assertions.assertThat(fromDB.get())
                .usingRecursiveComparison()
                .isEqualTo(created);
    }

    @Test
    void getUserPositive() {
        Random rand = new Random();
        Long idRand = userRepository.findAll().get(rand.nextInt(10)).getId();
        System.out.println(idRand);
        ResponseEntity<UserEntity> actual = restTemplate.getForEntity(buildUrl("/userinfo/") + idRand, UserEntity.class);
        Assertions.assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userRepository.findAll()).contains(actual.getBody());
        Assertions.assertThat(actual.getBody()).isIn(userRepository.findAll());
        Assertions.assertThat(actual.getBody()).isEqualTo(userRepository.findById(actual.getBody().getId()).get());
    }

    @Test
    void getStudentNegative() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(buildUrl("/userinfo/") + -1, String.class);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("User with id = %d not found".formatted(-1));
    }

    @Test
    void updatePositive() {
        Random rand = new Random();
        UserEntity user = userRepository.findAll().get(rand.nextInt(10));
        Long userId = user.getId();
        user.setFirstName("Updated Name");
        user.setEmail("Updated email");

        HttpEntity<UserEntity> request = new HttpEntity<UserEntity>(user);
        ResponseEntity<UserEntity> forEntity = restTemplate
                .exchange(buildUrl("/userinfo/") + userId, HttpMethod.PUT,
                        request, UserEntity.class);

        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userRepository.findAll()).contains(forEntity.getBody());
        Assertions.assertThat(Optional.of(user)).isEqualTo(userRepository.findById(user.getId()));
        Assertions.assertThat(user.getFirstName()).isEqualTo(userRepository.findById(userId).get().getFirstName());
        Assertions.assertThat(user.getEmail()).isEqualTo(userRepository.findById(userId).get().getEmail());
    }

    @Test
    void updateNegative() {
        Random rand = new Random();
        UserEntity user = userRepository.findAll().get(rand.nextInt(10));
        user.setId(-1L);
        Long userId = user.getId();

        HttpEntity<UserEntity> request = new HttpEntity<UserEntity>(user);
        ResponseEntity<String> forEntity = restTemplate
                .exchange(buildUrl("/userinfo/") + userId, HttpMethod.PUT, request, String.class);
        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(forEntity.getBody()).isEqualTo("User with id = %d not found".formatted(-1));
    }


    @Test
    void deletePositive() {
        Random rand = new Random();
        UserEntity user = userRepository.findAll().get(rand.nextInt(10));
        Long userId = user.getId();
        HttpEntity<UserEntity> request = new HttpEntity<>(user);
        ResponseEntity<UserEntity> forEntity = restTemplate
                .exchange(buildUrl("/userinfo/") + userId, HttpMethod.DELETE,
                        request, UserEntity.class);
        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userRepository.findAll()).doesNotContain(forEntity.getBody());
        Assertions.assertThat(forEntity.getBody()).isNotIn(userRepository.findAll());
        Assertions.assertThat(userRepository.findById(forEntity.getBody().getId())).isEmpty();
        Assertions.assertThat(userRepository.findById(forEntity.getBody().getId()))
                .isNotIn(userRepository.findAll());

    }

    @Test
    void deleteNegative() {
        Random rand = new Random();
        UserEntity user = userRepository.findAll().get(rand.nextInt(10));
        HttpEntity<UserEntity> request = new HttpEntity<>(user);
        ResponseEntity<String> forEntity = restTemplate
                .exchange(buildUrl("/userinfo/") + -1, HttpMethod.DELETE,
                        request, String.class);
        Assertions.assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(forEntity.getBody()).isEqualTo("User with id = %d not found".formatted(-1));


    }
}