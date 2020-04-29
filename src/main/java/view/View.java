package view;


import command.Command;
import command.Command2;
import command.CommandFactory;

import java.io.IOException;
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

    public Command getCommand() {
        Command command;
        try {
            String type;
            do {
                System.out.println("Type " + MainView.DELETE_COMMAND_LETTER + " if you want to delete info from the database");
                System.out.println("Type " + MainView.UPDATE_COMMAND_LETTER + " if you want to update an info in the database");
                System.out.println("Type " + MainView.SAVE_COMMAND_LETTER + " if you want to save info to the database");
                System.out.println("Type " + MainView.GET_COMMAND_LETTER + " if you want to get info from the database");
                System.out.println("Type q for quit");
                type = MainView.getReader().readLine();
                if ("q".equals(type)) {
                    System.exit(0);
                }
                command = CommandFactory.create(type);
            } while (command == null);
            return command;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract void execute(Command2 command);
}
