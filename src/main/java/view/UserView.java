package view;


import command.Command;
import controller.UserController;
import model.Post;
import model.Region;
import model.User;
import repository.CrudRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserView implements View {
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

    private void delete() {
        try {
            System.out.println("Type region id");
            String id = MainView.getReader().readLine();
            userController.deleteById(Long.parseLong(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User save() {
        try {
            System.out.println("Type firstName");
            String firstName = MainView.getReader().readLine();
            System.out.println("Type lastName");
            String lastName = MainView.getReader().readLine();
            System.out.println("Type posts ids within comma");
            String postsIds = MainView.getReader().readLine();
            List<Post> posts = Arrays.stream(postsIds.split(CrudRepository.DELIMITER))
                    .map(s -> new Post(Long.parseLong(s)))
                    .collect(Collectors.toList());
            System.out.println("Type region id");
            Long regionId = Long.parseLong(MainView.getReader().readLine());
            Region region = new Region(regionId, null);
            return userController.save(new User(firstName,
                    lastName,
                    posts,
                    region));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User update() {
        try {
            System.out.println("Type id");
            Long id = Long.parseLong(MainView.getReader().readLine());
            System.out.println("Type firstName");
            String firstName = MainView.getReader().readLine();
            System.out.println("Type lastName");
            String lastName = MainView.getReader().readLine();
            System.out.println("Type posts ids within comma");
            String postsIds = MainView.getReader().readLine();
            List<Post> posts = Arrays.stream(postsIds.split(CrudRepository.DELIMITER))
                    .map(s -> new Post(Long.parseLong(s)))
                    .collect(Collectors.toList());
            System.out.println("Type region id");
            Long regionId = Long.parseLong(MainView.getReader().readLine());
            Region region = new Region(regionId, null);
            return userController.update(new User(id,
                    firstName,
                    lastName,
                    posts,
                    region));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User get() {
        try {
            System.out.println("Type id");
            String id = MainView.getReader().readLine();
            return userController.getById(Long.parseLong(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<User> getAll() {
        return userController.getAll();
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                delete();
                return;
            case SAVE:
                System.out.println(save());
                return;
            case UPDATE:
                System.out.println(update());
                return;
            case GET_BY_ID:
                System.out.println(get());
                return;
            case GET_ALL:
                System.out.println(getAll());
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
