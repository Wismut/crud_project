package repository.csv;

import model.User;
import repository.PostRepository;
import repository.RegionRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class CsvUserRepositoryImpl implements UserRepository {
    private static final String USER_REPOSITORY_PATH = CSV_REPOSITORY_PATH + "/users.csv";
    private final String POST_IDS_DELIMITER = "_";
    private final PostRepository postRepository;
    private final RegionRepository regionRepository;

    public CsvUserRepositoryImpl(PostRepository postRepository, RegionRepository regionRepository) {
        this.postRepository = postRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public Optional<User> getById(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public User update(User user) {
        return null;
    }
}
