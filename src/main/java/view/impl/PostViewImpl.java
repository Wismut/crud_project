package view.impl;


import command.Command;
import controller.PostController;
import model.Post;
import view.PostView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class PostViewImpl implements PostView {
    private final PostController postController;
    public static final String LOCALDATETIME_PATTERN = "yyyy-MM-dd HH:mm";

    public PostViewImpl(PostController postController) {
        this.postController = postController;
    }

    private void deleteAndPrint() {
        String id = null;
        try {
            System.out.println("Type post id");
            id = MainView.getReader().readLine();
            postController.deleteById(Long.parseLong(id));
            System.out.println("Post with id = " + id + " was successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Post with id = " + id + " wasn't deleted");
        }
    }

    private void saveAndPrint() {
        String content = null;
        try {
            System.out.println("Type content");
            content = MainView.getReader().readLine();
            System.out.println("Type created date and time in the format " + LOCALDATETIME_PATTERN);
            System.out.println("For example " + LocalDateTime.now().format(DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
            String created = MainView.getReader().readLine();
            System.out.println("Type updated date and time in the format " + LOCALDATETIME_PATTERN);
            System.out.println("For example " + LocalDateTime.now().format(DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
            String updated = MainView.getReader().readLine();
            Post post = new Post(content,
                    LocalDateTime.parse(created, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)),
                    LocalDateTime.parse(updated, DateTimeFormatter.ofPattern(LOCALDATETIME_PATTERN)));
            Post savedPost = postController.save(post);
            System.out.println("New post with content = '" + content + "' and id = " + savedPost.getId() + " was successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("New post with content = '" + content + "' wasn't saved");
        }
    }

    private void updateAndPrint() {
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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Post with id = " + id + " wasn't updated");
        }
    }

    private void getOneAndPrint() {
        String id = null;
        try {
            System.out.println("Type post id");
            id = MainView.getReader().readLine();
            Optional<Post> post = postController.getById(Long.parseLong(id));
            if (post.isPresent()) {
                System.out.println(post);
            } else {
                System.out.println("Post with id = " + id + " wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Post with id = " + id + " wasn't found");
        }
    }

    void getAllAndPrint() {
        List<Post> posts = postController.getAll();
        if (posts.isEmpty()) {
            System.out.println("Posts list is empty");
        } else {
            System.out.println("Posts:");
            posts.forEach(System.out::println);
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
