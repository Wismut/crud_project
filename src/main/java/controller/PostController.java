package controller;

import model.Post;
import repository.PostRepository;
import repository.io.JavaIOPostRepository;

import java.util.List;
import java.util.Optional;

public class PostController {
    private final PostRepository postRepository;
    private static PostController instance;

    private PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public static PostController getInstance() {
        if (instance == null) {
            instance = new PostController(JavaIOPostRepository.getInstance());
        }
        return instance;
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post update(Post post) {
        return postRepository.update(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteBy(id);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Optional<Post> getById(Long id) {
        return postRepository.getById(id);
    }
}
