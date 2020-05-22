package repository;

import model.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Long> {
    List<Post> getByContentPart(String contentPart);
}
