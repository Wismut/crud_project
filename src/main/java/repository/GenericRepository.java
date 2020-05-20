package repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    String REPOSITORY_PATH = "src/main/resources/files";
    String CSV_REPOSITORY_PATH = REPOSITORY_PATH + "/csv";
    String TXT_REPOSITORY_PATH = REPOSITORY_PATH + "/txt";
    String DELIMITER = ",";
    String PREFIX = "{";
    String SUFFIX = "}";

    Optional<T> getById(ID id);

    List<T> getAll();

    T save(T entity);

    void deleteBy(ID id);

    T update(T entity);
}
