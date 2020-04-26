package repository;

import model.Post;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaIOPostRepository implements CRUDRepository<Post> {
    private final String POST_REPOSITORY_PATH = REPOSITORY_PATH + "/posts.txt";

    @Override
    public Post getById(Long id) {
        return getPostById(id);
    }

    @Override
    public List<Post> getAll() {
        return getAllPosts();
    }

    @Override
    public Post save(Post post) {
        Objects.requireNonNull(post);
        Long newId = createIdForNewRecord();
        post.setId(newId);
        writeToDatabase(Collections.singletonList(post), StandardOpenOption.APPEND);
        return post;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public Post update(Post post) {
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

    private void writeToDatabase(List<Post> posts, StandardOpenOption... openOptions) {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Paths.get(POST_REPOSITORY_PATH), openOptions)) {
            for (Post post : posts) {
                writer.write(post.getId() +
                        DELIMITER +
                        post.getContent() +
                        DELIMITER +
                        post.getCreated() +
                        DELIMITER +
                        post.getUpdated());
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long createIdForNewRecord() {
        Optional<Long> result = getAllIds()
                .stream()
                .max(Long::compare);
        return result.isPresent() ? result.get() + 1 : 1L;
    }

    private List<Long> getAllIds() {
        return getAllPosts()
                .stream()
                .map(Post::getId)
                .collect(Collectors.toList());
    }
}
