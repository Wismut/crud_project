package repository;

import model.Post;
import model.Region;
import model.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class JavaIOUserRepository implements CrudRepository<User> {
    private final String USER_REPOSITORY_PATH = REPOSITORY_PATH + "/users.txt";
    private final CrudRepository<Post> postRepository;
    private final CrudRepository<Region> regionRepository;

    public JavaIOUserRepository(CrudRepository<Post> postRepository, CrudRepository<Region> regionRepository) {
        this.postRepository = postRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public User getById(Long id) {
        return getUserById(id);
    }

    @Override
    public List<User> getAll() {
        return getAllUsers();
    }

    @Override
    public User save(User user) {
        Objects.requireNonNull(user);
        Long newId = createIdForNewRecord();
        user.setId(newId);
        writeToDatabase(Collections.singletonList(user), StandardOpenOption.APPEND);
        return user;
    }

    @Override
    public void deleteBy(Long id) {
        Objects.requireNonNull(id);
        List<User> filteredUsers = getAllUsers()
                .stream()
                .filter(r -> !id.equals(r.getId()))
                .collect(Collectors.toList());
        writeToDatabase(filteredUsers);
    }

    @Override
    public User update(User user) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getId());
        if (getUserById(user.getId()) == null) {
            return user;
        }
        List<User> filteredUsers = getAllUsers()
                .stream()
                .filter(u -> !user.getId().equals(u.getId()))
                .collect(Collectors.toList());
        filteredUsers.add(user);
        writeToDatabase(filteredUsers);
        return user;
    }

    private User getUserById(Long id) {
        Objects.requireNonNull(id);
        try {
            return getAllUsers()
                    .stream()
                    .filter(u -> id.equals(u.getId()))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<User> getAllUsers() {
        try {
            return Files.readAllLines(Paths.get(USER_REPOSITORY_PATH))
                    .stream()
                    .map(this::createUserFromDBString)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private void writeToDatabase(List<User> users, StandardOpenOption... openOptions) {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Paths.get(USER_REPOSITORY_PATH), openOptions)) {
            StringJoiner stringJoiner;
            for (User user : users) {
                stringJoiner = new StringJoiner(DELIMITER);
                stringJoiner
                        .add(user.getId().toString())
                        .add(user.getFirstName())
                        .add(user.getLastName())
                        .add(user.getPosts()
                                .stream()
                                .map(p -> p.getId().toString())
                                .collect(Collectors.joining(DELIMITER, PREFIX, SUFFIX)))
                        .add(user.getRegion().getId().toString());
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
        return getAllUsers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    private List<Post> getPostsByIds(List<Long> postIds) {
        return postRepository.getAll()
                .stream()
                .filter(p -> postIds.contains(p.getId()))
                .collect(Collectors.toList());
    }

    private User createUserFromDBString(String string) {
        String[] split = string.split("[" + PREFIX + SUFFIX + "]+");
        String[] idFirstNameLastName = split[0].split(DELIMITER);
        Long id = Long.parseLong(idFirstNameLastName[0]);
        String firstName = idFirstNameLastName[1];
        String lastName = idFirstNameLastName[2];
        List<Post> posts = getPostsByIds(Arrays.stream(split[1].split(DELIMITER))
                .map(Long::parseLong)
                .collect(Collectors.toList()));
        Region region = regionRepository.getById(Long.parseLong(split[2].split(DELIMITER)[1]));
        return new User(id,
                firstName,
                lastName,
                posts,
                region);
    }
}
