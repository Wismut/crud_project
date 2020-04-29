package command;

public interface Command<T> {
    void execute();

    T createEntityFromUserInput();
}
