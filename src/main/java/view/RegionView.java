package view;


import command.Command;
import controller.RegionController;
import model.Region;

import java.io.IOException;

public class RegionView implements View {
    private final RegionController regionController;
    private static RegionView instance;

    public static RegionView getInstance() {
        if (instance == null) {
            instance = new RegionView(RegionController.getInstance());
        }
        return instance;
    }

    private RegionView(RegionController regionController) {
        this.regionController = regionController;
    }

    private void delete() {
        String id = null;
        try {
            System.out.println("Type region id");
            id = MainView.getReader().readLine();
            regionController.deleteById(Long.parseLong(id));
            System.out.println("Region with id = " + id + " was successfully deleted");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Region with id = " + id + " wasn't deleted");
        }
    }

    private void save() {
        try {
            System.out.println("Type name");
            String name = MainView.getReader().readLine();
            regionController.save(new Region(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        try {
            System.out.println("Type id");
            Long id = Long.parseLong(MainView.getReader().readLine());
            System.out.println("Type new name");
            String name = MainView.getReader().readLine();
            regionController.update(new Region(id, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getOne() {
        try {
            System.out.println("Type id");
            String id = MainView.getReader().readLine();
            regionController.getById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAll() {
        regionController.getAll();
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                delete();
                return;
            case SAVE:
                save();
                return;
            case UPDATE:
                update();
                return;
            case GET_BY_ID:
                getOne();
                return;
            case GET_ALL:
                getAll();
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
