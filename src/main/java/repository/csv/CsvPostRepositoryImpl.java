package repository.csv;

import model.Post;
import repository.PostRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvPostRepositoryImpl implements PostRepository {
    private static final String POST_REPOSITORY_PATH = CSV_REPOSITORY_PATH + "/posts.csv";

    public CsvPostRepositoryImpl() {

    }

    @Override
    public Optional<Post> getById(Long id) {
        return getPostById(id);
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public Post update(Post post) {
        return null;
    }

    private Optional<Post> getPostById(Long id) {
        Objects.requireNonNull(id);
        try {
            return getAllPosts()
                    .stream()
                    .filter(p -> id.equals(p.getId()))
                    .findFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
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

    private LocalDateTime convertMillisecondsToLocalDateTime(long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(seconds * 1000),
                ZoneId.of(ZoneOffset.UTC.getId()));
    }
}
