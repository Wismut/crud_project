package controller;

import repository.PostRepository;
import repository.io.JavaIOPostRepository;

public class PostController {
    private final PostRepository postRepository;
    private static PostController instance;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public static PostController getInstance() {
        if (instance == null) {
            instance = new PostController(JavaIOPostRepository.getInstance());
        }
        return instance;
    }
}
