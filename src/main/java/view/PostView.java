package view;


import command.Command2;
import controller.PostController;
import model.Post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostView extends View<Post, Long> {
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

    @Override
    void deleteById(Long aLong) {

    }

    @Override
    Post save(Post entity) {
        return null;
    }

    @Override
    Post update(Post entity) {
        return null;
    }

    @Override
    Post getById(Long aLong) {
        return null;
    }

    @Override
    List<Post> getAll() {
        return null;
    }

    @Override
    public void execute(Command2 command) {
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
                    System.out.println("Type content");
                    String content = MainView.getReader().readLine();
                    System.out.println("Type created date and time in the format " + LOCALDATETIME_PATTERN);
                    String created = MainView.getReader().readLine();
                    System.out.println("Type updated date and time in the format " + LOCALDATETIME_PATTERN);
                    String updated = MainView.getReader().readLine();
                    Post post = new Post(content,
                            LocalDateTime.parse(created, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)),
                            LocalDateTime.parse(updated, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
                    postController.update(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
        }
    }
}
