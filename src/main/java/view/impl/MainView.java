package view.impl;


import command.Command;
import view.View;
import view.ViewFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainView {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        View view = this.readViewType();
        Command command = view.getCommand();
        view.execute(command);
    }

    public View readViewType() {
        View view;
        try {
            String type;
            do {
                System.out.println("Type " + View.USER_NUMBER + " or " + View.USER_LETTER + " if you want to work with users info");
                System.out.println("Type " + View.POST_NUMBER + " or " + View.POST_LETTER + " if you want to work with posts info");
                System.out.println("Type " + View.REGION_NUMBER + " or " + View.REGION_LETTER + " if you want to work with regions info");
                System.out.println("Type q for quit");
                type = reader.readLine();
                if ("q".equals(type)) {
                    System.exit(0);
                }
                view = ViewFactory.create(type);
            } while (view == null);
            return view;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can't read view type");
    }

    public static BufferedReader getReader() {
        return reader;
    }
}
