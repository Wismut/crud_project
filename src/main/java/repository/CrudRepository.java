package repository;

import java.util.List;

public interface CrudRepository<T> {
    String REPOSITORY_PATH = "src/main/resources/files";
    String DELIMITER = ",";
    String PREFIX = "{";
    String SUFFIX = "}";

    T getById(Long id);

    List<T> getAll();

    T save(T region);

    void deleteBy(Long id);

    T update(T region);
}
