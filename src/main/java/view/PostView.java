package view;


import command.Command;
import controller.PostController;
import model.Post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostView implements View {
    private final PostController postController;
    private static PostView instance;
    public static final String LOCALDATETIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static PostView getInstance() {
        if (instance == null) {
            instance = new PostView(PostController.getInstance());
        }
        return instance;
    }

    private PostView(PostController postController) {
        this.postController = postController;
    }

    private void delete() {
        String id = null;
        try {
            System.out.println("Type post id");
            id = MainView.getReader().readLine();
            postController.deleteById(Long.parseLong(id));
            System.out.println("Post with id = " + id + " was successfully deleted");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Post with id = " + id + " wasn't deleted");
        }
    }

    private void save() {
        String content = null;
        try {
            System.out.println("Type content");
            content = MainView.getReader().readLine();
            System.out.println("Type created date and time in the format " + LOCALDATETIME_PATTERN);
            String created = MainView.getReader().readLine();
            System.out.println("Type updated date and time in the format " + LOCALDATETIME_PATTERN);
            String updated = MainView.getReader().readLine();
            Post post = new Post(content,
                    LocalDateTime.parse(created, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)),
                    LocalDateTime.parse(updated, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
            postController.save(post);
            System.out.println("New post with content = '" + content + "' was successfully saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("New post with content = '" + content + "' wasn't saved");
        }
    }

    private void update() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            System.out.println("Type new content");
            String content = MainView.getReader().readLine();
            System.out.println("Type new created date and time in the format " + LOCALDATETIME_PATTERN);
            String created = MainView.getReader().readLine();
            System.out.println("Type new updated date and time in the format " + LOCALDATETIME_PATTERN);
            String updated = MainView.getReader().readLine();
            Post post = new Post(Long.parseLong(id),
                    content,
                    LocalDateTime.parse(created, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)),
                    LocalDateTime.parse(updated, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
            postController.update(post);
            System.out.println("Post with id = " + id + " was successfully updated");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Post with id = " + id + " wasn't updated");
        }
    }

    private void getOne() {
        String id = null;
        try {
            System.out.println("Type post id");
            id = MainView.getReader().readLine();
            Post post = postController.getById(Long.parseLong(id));
            if (post != null) {
                System.out.println(post);
            } else {
                System.out.println("Post with id = " + id + " wasn't found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Post with id = " + id + " wasn't found");
        }
    }

    void getAll() {
        List<Post> posts = postController.getAll();
        System.out.println("Posts:");
        posts.stream()
                .forEach(System.out::println);
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                delete();
                return;
            case SAVE:
                save();
                return;
            case UPDATE:
                update();
                return;
            case GET_BY_ID:
                getOne();
                return;
            case GET_ALL:
                getAll();
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
