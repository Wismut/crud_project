package command;


import query_build_info.QueryBuildInfo;
import view.View;

public class GetCommand<T> implements Command<T>, QueryBuildInfo<T> {
    View view;

    public GetCommand(View view) {
        this.view = view;
    }

    @Override
    public void execute() {

    }

    @Override
    public T get() {
        return null;
    }
}
