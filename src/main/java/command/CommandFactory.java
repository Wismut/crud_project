package command;


import view.MainView;

public class CommandFactory {
    public static Command create(String type) {
        switch (type) {
            case MainView.DELETE_COMMAND_LETTER:
                return Command.DELETE_BY_ID;
            case MainView.GET_COMMAND_LETTER:
                return Command.GET_BY_ID;
            case MainView.SAVE_COMMAND_LETTER:
                return Command.SAVE;
            case MainView.UPDATE_COMMAND_LETTER:
                return Command.UPDATE;
            default:
                return null;
        }
    }
}
