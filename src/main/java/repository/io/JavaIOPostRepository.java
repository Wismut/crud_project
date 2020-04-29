package repository.io;

import model.Post;
import repository.PostRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class JavaIOPostRepository implements PostRepository {
    private static final String POST_REPOSITORY_PATH = REPOSITORY_PATH + "/posts.txt";
    private static PostRepository instance;

    private JavaIOPostRepository() {

    }

    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new JavaIOPostRepository();
        }
        return instance;
    }

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
        Post oldPost = getPostById(post.getId());
        if (oldPost == null) {
            return post;
        }
        List<Post> filteredPosts = getAllPosts()
                .stream()
                .filter(p -> !post.getId().equals(p.getId()))
                .collect(Collectors.toList());
        filteredPosts.add(merge(oldPost, post));
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
            StringJoiner stringJoiner;
            for (Post post : posts) {
                stringJoiner = new StringJoiner(DELIMITER);
                stringJoiner
                        .add(post.getId().toString())
                        .add(post.getContent())
                        .add(post.getCreated() == null ?
                                String.valueOf(convertLocalDateTimeToSeconds(LocalDateTime.now())) :
                                String.valueOf(convertLocalDateTimeToSeconds(post.getCreated())))
                        .add(post.getUpdated() == null ?
                                String.valueOf(convertLocalDateTimeToSeconds(LocalDateTime.now())) :
                                String.valueOf(convertLocalDateTimeToSeconds(post.getUpdated())));
                writer.write(stringJoiner.toString());
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
        return result.map(x -> x + 1).orElse(1L);
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

    private Post merge(Post oldPost, Post newPost) {
        if (newPost.getContent() == null) {
            newPost.setContent(oldPost.getContent());
        }
        if (newPost.getCreated() == null) {
            newPost.setCreated(oldPost.getCreated());
        }
        if (newPost.getUpdated() == null) {
            newPost.setUpdated(oldPost.getUpdated());
        }
        return newPost;
    }
}
