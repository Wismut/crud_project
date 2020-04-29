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
        try {
            System.out.println("Type post id");
            String id = MainView.getReader().readLine();
            postController.deleteById(Long.parseLong(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Post save() {
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
            return postController.save(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Post update() {
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
            return postController.update(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Post getOne() {
        try {
            System.out.println("Type post id");
            String id = MainView.getReader().readLine();
            return postController.getById(Long.parseLong(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<Post> getAll() {
        return postController.getAll();
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
                getOne();
                return;
            case GET_ALL:
                System.out.println(getAll());
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
