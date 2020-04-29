package view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainView {
    public static final String SAVE_COMMAND_LETTER = "s";
    public static final String GET_COMMAND_LETTER = "g";
    public static final String DELETE_COMMAND_LETTER = "d";
    public static final String UPDATE_COMMAND_LETTER = "u";

    public View readViewType() {
        View view;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
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
        return null;
    }
}
