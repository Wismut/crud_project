package controller;

import model.User;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
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

    public Optional<User> getById(Long id) {
        return userRepository.getById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
