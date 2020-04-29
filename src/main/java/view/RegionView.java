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

    private void deleteById(Long id) {
        regionController.deleteById(id);
    }

    private Region save(Region region) {
        return regionController.save(region);
    }

    private Region update(Region region) {
        return regionController.update(region);
    }

    private Region getById(Long id) {
        return regionController.getById(id);
    }

    private List<Region> getAll() {
        return regionController.getAll();
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                try {
                    System.out.println("Type region id");
                    String id = MainView.getReader().readLine();
                    regionController.deleteById(Long.parseLong(id));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case SAVE:
                try {
                    System.out.println("Type name");
                    String name = MainView.getReader().readLine();
                    regionController.save(new Region(name));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case UPDATE:
                try {
                    System.out.println("Type id");
                    Long id = Long.parseLong(MainView.getReader().readLine());
                    System.out.println("Type new name");
                    String name = MainView.getReader().readLine();
                    regionController.update(new Region(id, name));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case GET_BY_ID:
                try {
                    System.out.println("Type id");
                    String id = MainView.getReader().readLine();
                    Region region = regionController.getById(Long.parseLong(id));
                    System.out.println(region);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            case GET_ALL:
                List<Region> regions = regionController.getAll();
                System.out.println(regions);
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
