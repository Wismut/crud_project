package view;


import controller.PostController;
import model.Post;

import java.util.List;

public class PostView extends View<Post,Long> {
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
}
