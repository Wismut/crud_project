package view;


import command.Command;
import controller.RegionController;
import model.Region;

import java.io.IOException;
import java.util.List;

public class RegionView implements View<Region, Long> {
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
        try {
            System.out.println("Type region id");
            String id = MainView.getReader().readLine();
            regionController.deleteById(Long.parseLong(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Region save() {
        try {
            System.out.println("Type name");
            String name = MainView.getReader().readLine();
            return regionController.save(new Region(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Region update() {
        try {
            System.out.println("Type id");
            Long id = Long.parseLong(MainView.getReader().readLine());
            System.out.println("Type new name");
            String name = MainView.getReader().readLine();
            return regionController.update(new Region(id, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Region getOne() {
        try {
            System.out.println("Type id");
            String id = MainView.getReader().readLine();
            return regionController.getById(Long.parseLong(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Region> getAll() {
        return regionController.getAll();
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                delete();
                return;
            case SAVE:
                System.out.println(save());
                return;
            case UPDATE:
                System.out.println(update());
                return;
            case GET_BY_ID:
                System.out.println(getOne());
                return;
            case GET_ALL:
                System.out.println(getAll());
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
