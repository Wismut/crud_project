package view;


import command.Command;
import controller.UserController;
import model.Post;
import model.Region;
import model.User;
import repository.GenericRepository;

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

    private void deleteAndPrint() {
        String id = null;
        try {
            System.out.println("Type user id");
            id = MainView.getReader().readLine();
            userController.deleteById(Long.parseLong(id));
            System.out.println("User with id = " + id + " was deleted");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("User with id = " + id + " wasn't deleted");
        }
    }

    private void saveAndPrint() {
        try {
            System.out.println("Type firstName");
            String firstName = MainView.getReader().readLine();
            System.out.println("Type lastName");
            String lastName = MainView.getReader().readLine();
            System.out.println("Type posts ids within comma");
            String postsIds = MainView.getReader().readLine();
            List<Post> posts = Arrays.stream(postsIds.split(GenericRepository.DELIMITER))
                    .map(s -> new Post(Long.parseLong(s)))
                    .collect(Collectors.toList());
            System.out.println("Type region id");
            Long regionId = Long.parseLong(MainView.getReader().readLine());
            Region region = new Region(regionId, null);
            User user = userController.save(new User(firstName,
                    lastName,
                    posts,
                    region));
            System.out.println("New user with id = " + user.getId() + " was saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("New user wasn't saved");
        }
    }

    private void updateAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            System.out.println("Type firstName");
            String firstName = MainView.getReader().readLine();
            System.out.println("Type lastName");
            String lastName = MainView.getReader().readLine();
            System.out.println("Type posts ids within comma");
            String postsIds = MainView.getReader().readLine();
            List<Post> posts = Arrays.stream(postsIds.split(GenericRepository.DELIMITER))
                    .map(s -> new Post(Long.parseLong(s)))
                    .collect(Collectors.toList());
            System.out.println("Type region id");
            Long regionId = Long.parseLong(MainView.getReader().readLine());
            Region region = new Region(regionId, null);
            userController.update(new User(Long.parseLong(id),
                    firstName,
                    lastName,
                    posts,
                    region));
            System.out.println("User with id = " + id + " was updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("User with id = " + id + " wasn't updated");
        }
    }

    private void getOneAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            User user = userController.getById(Long.parseLong(id));
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("User with id = " + id + " wasn't found");
        }
    }

    private void getAllAndPrint() {
        List<User> users = userController.getAll();
        if (users.isEmpty()) {
            System.out.println("Users list is empty");
        } else {
            System.out.println("Users:");
            users.stream()
                    .forEach(System.out::println);
        }
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                deleteAndPrint();
                return;
            case SAVE:
                saveAndPrint();
                return;
            case UPDATE:
                updateAndPrint();
                return;
            case GET_BY_ID:
                getOneAndPrint();
                return;
            case GET_ALL:
                getAllAndPrint();
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
