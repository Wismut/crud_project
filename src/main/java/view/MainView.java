package view;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainView {
    public void readViewType() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Type 1 if you want to work with users info");
            System.out.println("Type 2 if you want to work with posts info");
            System.out.println("Type 3 if you want to work with regions info");
            System.out.println("Type q for quit");
        } while (true);
    }
}
