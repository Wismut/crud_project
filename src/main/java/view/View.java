package view;


import command.Command;

import java.util.List;

public abstract class View<T, ID> {
    static final String USER_NUMBER = "1";
    static final String POST_NUMBER = "2";
    static final String REGION_NUMBER = "3";
    static final String USER_LETTER = "u";
    static final String POST_LETTER = "p";
    static final String REGION_LETTER = "r";
    T entity;

    abstract void deleteById(ID id);

    abstract T save(T entity);

    abstract T update(T entity);

    abstract T getById(ID id);

    abstract List<T> getAll();

    public abstract Command getCommand();
}
