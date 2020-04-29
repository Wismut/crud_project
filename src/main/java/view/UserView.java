package view;


import command.Command;
import controller.UserController;
import model.User;

import java.util.List;

public class UserView extends View<User,Long> {
    private final UserController userController;
    private static UserView instance;

    public static UserView getInstance() {
        if (instance == null) {
            instance = new UserView(UserController.getInstance());
        }
        return instance;
    }

    private UserView(UserController userController) {
        this.userController = userController;
    }

    @Override
    void deleteById(Long aLong) {

    }

    @Override
    User save(User entity) {
        return null;
    }

    @Override
    User update(User entity) {
        return null;
    }

    @Override
    User getById(Long aLong) {
        return null;
    }

    @Override
    List<User> getAll() {
        return null;
    }

    @Override
    public void execute(Command command) {

    }
}
