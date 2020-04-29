package view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainView {
    public void readViewType() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        View view = null;
        do {
            System.out.println("Type 1 if you want to work with users info");
            System.out.println("Type 2 if you want to work with posts info");
            System.out.println("Type 3 if you want to work with regions info");
            System.out.println("Type q for quit");
            String type = null;
            try {
                type = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            if ("q".equals(type)) {
                System.exit(0);
            }
            view = ViewFactory.create(type);
        } while (view == null);
    }
}
