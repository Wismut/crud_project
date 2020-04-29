package view;


import command.Command;
import command.CommandFactory;

import java.io.IOException;

public interface View {
    String USER_NUMBER = "1";
    String POST_NUMBER = "2";
    String REGION_NUMBER = "3";
    String USER_LETTER = "u";
    String POST_LETTER = "p";
    String REGION_LETTER = "r";

    default Command getCommand() {
        Command command;
        try {
            String type;
            do {
                System.out.println("Type " + MainView.DELETE_COMMAND_LETTER + " if you want to delete record from the database");
                System.out.println("Type " + MainView.UPDATE_COMMAND_LETTER + " if you want to update record in the database");
                System.out.println("Type " + MainView.SAVE_COMMAND_LETTER + " if you want to save record to the database");
                System.out.println("Type " + MainView.GET_COMMAND_LETTER + " if you want to get one record from the database");
                System.out.println("Type " + MainView.GET_ALL_COMMAND_LETTER + " if you want to get all records from the database");
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

    void execute(Command command);
}
