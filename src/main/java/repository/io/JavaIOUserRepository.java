package repository.io;

import model.Post;
import model.Region;
import model.User;
import repository.GenericRepository;
import repository.UserRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaIOUserRepository implements UserRepository {
    private final String USER_REPOSITORY_PATH = REPOSITORY_PATH + "/users.txt";
    private final String POST_IDS_DELIMITER = "_";
    private final GenericRepository<Post, Long> postRepository;
    private final GenericRepository<Region, Long> regionRepository;
    private static UserRepository instance;

    private JavaIOUserRepository(GenericRepository<Post, Long> postRepository, GenericRepository<Region, Long> regionRepository) {
        this.postRepository = postRepository;
        this.regionRepository = regionRepository;
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new JavaIOUserRepository(JavaIOPostRepository.getInstance(),
                    JavaIORegionRepository.getInstance());
        }
        return instance;
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
        User oldUser = getUserById(user.getId());
        if (oldUser == null) {
            return user;
        }
        List<User> filteredUsers = getAllUsers()
                .stream()
                .filter(u -> !user.getId().equals(u.getId()))
                .collect(Collectors.toList());
        filteredUsers.add(merge(oldUser, user));
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
                                .collect(Collectors.joining(POST_IDS_DELIMITER, PREFIX, SUFFIX)))
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
        String[] split = string.split(DELIMITER);
        Long id = Long.parseLong(split[0]);
        String firstName = split[1];
        String lastName = split[2];
        List<Post> posts;
        if (split[3].matches(".*\\d.*")) {
            String[] postIds = split[3]
                    .replaceFirst("\\" + PREFIX, "")
                    .replaceFirst(SUFFIX, "")
                    .split(POST_IDS_DELIMITER);
            posts = getPostsByIds(Arrays.stream(postIds)
                    .map(Long::parseLong)
                    .collect(Collectors.toList()));
        } else {
            posts = Collections.emptyList();
        }
        Region region = regionRepository.getById(Long.parseLong(split[4]));
        return new User(id,
                firstName,
                lastName,
                posts,
                region);
    }

    public User merge(User oldUser, User newUser) {
        if (newUser.getFirstName() == null) {
            newUser.setFirstName(oldUser.getFirstName());
        }
        if (newUser.getLastName() == null) {
            newUser.setLastName(oldUser.getLastName());
        }
        if (newUser.getPosts() == null || newUser.getPosts().isEmpty()) {
            newUser.setPosts(oldUser.getPosts());
        } else {
            List<Post> mergedPostsIds = new ArrayList<>(oldUser.getPosts());
            mergedPostsIds.retainAll(newUser.getPosts());
            newUser.setPosts(Stream.concat(newUser
                            .getPosts()
                            .stream(),
                    oldUser
                            .getPosts()
                            .stream())
                    .filter(p -> !mergedPostsIds.contains(p))
                    .collect(Collectors.toList()));
        }
        if (newUser.getRegion() == null) {
            newUser.setRegion(oldUser.getRegion());
        }
        return newUser;
    }
}
