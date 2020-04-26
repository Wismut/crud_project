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
import java.time.ZoneOffset;
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
        Objects.requireNonNull(id);
        List<Post> filteredPosts = getAllPosts()
                .stream()
                .filter(p -> !id.equals(p.getId()))
                .collect(Collectors.toList());
        writeToDatabase(filteredPosts);
    }

    @Override
    public Post update(Post post) {
        Objects.requireNonNull(post);
        Objects.requireNonNull(post.getId());
        List<Post> allPosts = getAllPosts();
        if (getPostById(post.getId()) == null) {
            return post;
        }
        List<Post> filteredPosts = allPosts
                .stream()
                .filter(p -> !post.getId().equals(p.getId()))
                .collect(Collectors.toList());
        filteredPosts.add(post);
        writeToDatabase(filteredPosts);
        return post;
    }

    private Post getPostById(Long id) {
        Objects.requireNonNull(id);
        try {
            return getAllPosts()
                    .stream()
                    .filter(p -> id.equals(p.getId()))
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
                                convertMillisecondsToLocalDateTime(Long.parseLong(split[2])),
                                convertMillisecondsToLocalDateTime(Long.parseLong(split[3])));
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
                        (post.getCreated() == null ?
                                convertLocalDateTimeToSeconds(LocalDateTime.now()) :
                                convertLocalDateTimeToSeconds(post.getCreated())) +
                        DELIMITER +
                        (post.getUpdated() == null ?
                                convertLocalDateTimeToSeconds(LocalDateTime.now()) :
                                convertLocalDateTimeToSeconds(post.getUpdated())));
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

    private LocalDateTime convertMillisecondsToLocalDateTime(long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(seconds * 1000),
                ZoneId.of(ZoneOffset.UTC.getId()));
    }

    private long convertLocalDateTimeToSeconds(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
