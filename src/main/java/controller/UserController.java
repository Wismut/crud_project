package controller;

import model.User;
import repository.UserRepository;
import repository.io.JavaIOUserRepository;

import java.util.List;

public class UserController {
    private final UserRepository userRepository;
    private static UserController instance;

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController(JavaIOUserRepository.getInstance());
        }
        return instance;
    }

    private UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteBy(id);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public User getById(Long id){
        return userRepository.getById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
