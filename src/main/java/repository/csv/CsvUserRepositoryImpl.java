package repository.csv;

import model.User;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class CsvUserRepositoryImpl implements UserRepository {
    private static final String USER_REPOSITORY_PATH = REPOSITORY_PATH + "/users.csv";

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
