package repository;

import java.util.List;

public interface CRUDRepository<T> {
    String REPOSITORY_PATH = "src/main/resources/files";
    String DELIMITER = ",";

    T getById(Long id);

    List<T> getAll();

    T save(T region);

    void deleteBy(Long id);

    T update(T region);
}
