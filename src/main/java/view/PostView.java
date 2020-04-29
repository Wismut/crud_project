package view;


import command.Command;
import command.Command2;
import controller.PostController;
import model.Post;

import java.util.List;

public class PostView extends View<Post, Long> {
    private final PostController postController;
    private static PostView instance;

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
    public Command getCommand() {
        return super.getCommand();
    }

    @Override
    public void execute(Command2 command) {
        switch (command) {
            case DELETE_BY_ID:
                postController.deleteById(1L);
        }
    }
}
