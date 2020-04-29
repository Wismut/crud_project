package repository;

import java.util.List;

public interface CrudRepository<T, ID> {
    String REPOSITORY_PATH = "src/main/resources/files";
    String DELIMITER = ",";
    String PREFIX = "{";
    String SUFFIX = "}";

    T getById(ID id);

    List<T> getAll();

    T save(T entity);

    void deleteBy(ID id);

    T update(T entity);
}
