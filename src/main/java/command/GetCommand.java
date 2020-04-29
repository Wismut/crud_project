package command;


import view.View;

public class GetCommand<T> implements Command<T> {
    View view;

    @Override
    public void execute() {

    }

    @Override
    public T createEntityFromUserInput() {
        return null;
    }
}
