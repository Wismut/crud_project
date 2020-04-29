package view;


import command.Command;
import command.CommandFactory;
import controller.PostController;
import model.Post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PostView extends View<Post,Long> {
    private final PostController postController;
    private static PostView instance;

    public static PostView getInstance() {
        if (instance == null) {
            instance = new PostView(PostController.getInstance());
        }
        return instance;
    }

    private PostView(PostController postController) {
        this.postController = postController;
    }

    @Override
    void deleteById(Long aLong) {

    }

    @Override
    Post save(Post entity) {
        return null;
    }

    @Override
    Post update(Post entity) {
        return null;
    }

    @Override
    Post getById(Long aLong) {
        return null;
    }

    @Override
    List<Post> getAll() {
        return null;
    }

    @Override
    public Command getCommand() {
        Command command;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String type;
            do {
                System.out.println("Type " + MainView.DELETE_COMMAND_LETTER + " if you want to delete info from the database");
                System.out.println("Type " + MainView.UPDATE_COMMAND_LETTER + " if you want to update info in the database");
                System.out.println("Type " + MainView.SAVE_COMMAND_LETTER + " if you want to save info to database");
                System.out.println("Type " + MainView.GET_COMMAND_LETTER + " if you want to get info from database");
                System.out.println("Type q for quit");
                type = reader.readLine();
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
}
