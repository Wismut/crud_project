package repository;

import model.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class JavaIOUserRepository implements CrudRepository<User> {
    private static final String USER_REPOSITORY_PATH = REPOSITORY_PATH + "/users.txt";
    private JavaIOPostRepository postRepository = new JavaIOPostRepository();
    private JavaIORegionRepository regionRepository = new JavaIORegionRepository();

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
    public User update(User region) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(region.getId());
        List<User> allRegions = getAllUsers();
        if (getUserById(region.getId()) == null) {
            return region;
        }
        List<User> filteredUsers = allRegions
                .stream()
                .filter(r -> !region.getId().equals(r.getId()))
                .collect(Collectors.toList());
        filteredUsers.add(region);
        writeToDatabase(filteredUsers);
        return region;
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
                    .map(s -> {
                        String[] split = s.split(DELIMITER);
                        return new User(Long.parseLong(split[0]),
                                split[1],
                                split[2],
                                postRepository.getAll(),
                                regionRepository.getById(1L));
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private void writeToDatabase(List<User> users, StandardOpenOption... openOptions) {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Paths.get(USER_REPOSITORY_PATH), openOptions)) {
            for (User user : users) {
                StringJoiner stringJoiner = new StringJoiner(DELIMITER);
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
        return result.isPresent() ? result.get() + 1 : 1L;
    }

    private List<Long> getAllIds() {
        return getAllUsers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

}
