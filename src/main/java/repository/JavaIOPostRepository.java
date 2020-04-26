package repository;

import model.Post;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JavaIOPostRepository implements CRUDRepository<Post> {
    private final String POST_REPOSITORY_PATH = REPOSITORY_PATH + "/posts.txt";

    @Override
    public Post getById(Long id) {
        return getPostById(id);
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
        try {
            return Files.readAllLines(Paths.get(POST_REPOSITORY_PATH))
                    .stream()
                    .map(s -> {
                        String[] split = s.split(DELIMITER);
                        return new Post(Long.parseLong(split[0]),
                                split[1],
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(split[2])), ZoneId.systemDefault()),
                                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(split[3])), ZoneId.systemDefault()));
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
