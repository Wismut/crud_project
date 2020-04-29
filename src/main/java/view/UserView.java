package view;


import command.Command;
import controller.UserController;
import model.User;

import java.io.IOException;
import java.util.List;

public class UserView implements View<User, Long> {
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

    void deleteById(Long aLong) {

    }

    User save(User entity) {
        return null;
    }

    User update(User entity) {
        return null;
    }

    User getById(Long id) {
        return null;
    }

    List<User> getAll() {
        return null;
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                try {
                    System.out.println("Type region id");
                    String id = MainView.getReader().readLine();
                    userController.deleteById(Long.parseLong(id));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case SAVE:
                try {
                    System.out.println("Type firstName");
                    String firstName = MainView.getReader().readLine();
                    System.out.println("Type lastName");
                    String lastName = MainView.getReader().readLine();
                    System.out.println("Type posts ids within comma");
                    String postsIds = MainView.getReader().readLine();
                    System.out.println("Type region id");
                    String regionId = MainView.getReader().readLine();
                    userController.save(new User());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case UPDATE:
                try {
                    System.out.println("Type id");
                    Long id = Long.parseLong(MainView.getReader().readLine());
                    System.out.println("Type new name");
                    String name = MainView.getReader().readLine();
                    userController.update(new User());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case GET_BY_ID:
                try {
                    System.out.println("Type id");
                    String id = MainView.getReader().readLine();
                    User user = userController.getById(Long.parseLong(id));
                    System.out.println(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case GET_ALL:
                List<User> users = userController.getAll();
                System.out.println(users);
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }

    }
}
