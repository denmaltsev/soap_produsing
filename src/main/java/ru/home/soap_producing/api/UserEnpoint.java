package ru.home.soap_producing.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.home.springsoap.entity.*;
import ru.home.soap_producing.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Denis Maltsev on 04.05.2020.
 */
@Endpoint
public class UserEnpoint {
    public static final String NAMESPACE_URI = "http://home.ru/springsoap/entity";

    private UserRepository userRepository;

    @Autowired
    public UserEnpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();

        Optional<UserType> userByName = userRepository.getUserByName(request.getName());
        if (userByName.isPresent()) {
            response.setUser(userByName.get());
            response.setStatus("OK");
        } else {
            response.setStatus("Error");
            ErrorType error = new ErrorType();
            error.setCode("900");
            error.setMessage("User not found for name " + request.getName());

            ErrorListType errorListType = new ErrorListType();
            errorListType.getError().add(error);
            response.setErrors(errorListType);
        }

        return response;
    }
}
