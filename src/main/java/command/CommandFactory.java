package command;


import view.MainView;

public class CommandFactory {
    public static Command2 create(String type) {
        switch (type) {
            case MainView.DELETE_COMMAND_LETTER:
                return Command2.DELETE_BY_ID;
            case MainView.GET_COMMAND_LETTER:
                return Command2.GET_BY_ID;
            case MainView.SAVE_COMMAND_LETTER:
                return Command2.SAVE;
            case MainView.UPDATE_COMMAND_LETTER:
                return Command2.UPDATE;
            default:
                return null;
        }
    }
}
