package command;


import view.MainView;

public class CommandFactory {
    public static Command create(String type) {
        switch (type) {
            case MainView.DELETE_COMMAND_LETTER:
                return new DeleteCommand();
            case MainView.GET_COMMAND_LETTER:
                return new GetCommand();
            case MainView.SAVE_COMMAND_LETTER:
                return new SaveCommand();
            case MainView.UPDATE_COMMAND_LETTER:
                return new UpdateCommand();
            default:
                return null;
        }
    }
}
