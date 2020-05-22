package controller;

import model.Post;
import repository.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
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
