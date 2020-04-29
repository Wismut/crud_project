package view;


import command.Command;
import controller.PostController;
import model.Post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostView implements View<Post,Long> {
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

    void deleteById(Long aLong) {

    }

    Post save(Post entity) {
        return null;
    }

    Post update(Post entity) {
        return null;
    }

    Post getById(Long aLong) {
        return null;
    }

    List<Post> getAll() {
        return null;
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                try {
                    System.out.println("Type post id");
                    String id = MainView.getReader().readLine();
                    postController.deleteById(Long.parseLong(id));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case SAVE:
                try {
                    System.out.println("Type content");
                    String content = MainView.getReader().readLine();
                    System.out.println("Type created date and time in the format " + LOCALDATETIME_PATTERN);
                    String created = MainView.getReader().readLine();
                    System.out.println("Type updated date and time in the format " + LOCALDATETIME_PATTERN);
                    String updated = MainView.getReader().readLine();
                    Post post = new Post(content,
                            LocalDateTime.parse(created, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)),
                            LocalDateTime.parse(updated, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
                    postController.save(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case UPDATE:
                try {
                    System.out.println("Type id");
                    Long id = Long.parseLong(MainView.getReader().readLine());
                    System.out.println("Type new content");
                    String content = MainView.getReader().readLine();
                    System.out.println("Type new created date and time in the format " + LOCALDATETIME_PATTERN);
                    String created = MainView.getReader().readLine();
                    System.out.println("Type new updated date and time in the format " + LOCALDATETIME_PATTERN);
                    String updated = MainView.getReader().readLine();
                    Post post = new Post(id,
                            content,
                            LocalDateTime.parse(created, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)),
                            LocalDateTime.parse(updated, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
                    postController.update(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case GET_BY_ID:
                try {
                    System.out.println("Type post id");
                    String id = MainView.getReader().readLine();
                    Post post = postController.getById(Long.parseLong(id));
                    System.out.println(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case GET_ALL:
                List<Post> posts = postController.getAll();
                System.out.println(posts);
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
