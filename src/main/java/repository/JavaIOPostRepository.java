package repository;

import model.Post;


import java.util.List;
import java.util.Objects;

public class JavaIOPostRepository implements CRUDRepository<Post> {
    private final String POST_REPOSITORY_PATH = REPOSITORY_PATH + "/posts.txt";

    @Override
    public Post getById(Long id) {
        return null;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post save(Post region) {
        return null;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public Post update(Post region) {
        return null;
    }

    private Post getPostById(Long id) {
        Objects.requireNonNull(id);
        try {
            return getAllPosts()
                    .stream()
                    .filter(r -> id.equals(r.getId()))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Post> getAllPosts() {
        return null;
    }
}
