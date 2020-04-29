package controller;

import repository.UserRepository;
import repository.io.JavaIOUserRepository;

public class UserController {
    private final UserRepository userRepository;
    private static UserController instance;

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController(JavaIOUserRepository.getInstance());
        }
        return instance;
    }

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
