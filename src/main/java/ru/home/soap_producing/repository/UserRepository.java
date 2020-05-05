package ru.home.soap_producing.repository;

import org.springframework.stereotype.Component;
import ru.home.springsoap.entity.UserType;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Denis Maltsev on 04.05.2020.
 */
@Component
public class UserRepository {
    private static Map<String, UserType> users = new HashMap<>();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 9; i++) {
            UserType user = new UserType();
            user.setName("user" + i);
            user.setAge(i * 10);
            user.setProfession("profession" +  i);
            users.put("user" + i, user);
        }
    }

    public Optional<UserType> getUserByName(String name) {

        if (users.get(name) == null) {
            return Optional.empty();
        } else {
            return Optional.of(users.get(name));
        }
    }

    public List<UserType> getAllUsers() {
        return (List<UserType>) users.values();
    }
}
