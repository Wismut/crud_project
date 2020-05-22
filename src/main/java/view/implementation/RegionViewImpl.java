package view.implementation;


import command.Command;
import controller.RegionController;
import model.Region;
import view.RegionView;

import java.util.List;
import java.util.Optional;

public class RegionViewImpl implements RegionView {
    private final RegionController regionController;

    public RegionViewImpl(RegionController regionController) {
        this.regionController = regionController;
    }

    private void deleteAndPrint() {
        String id = null;
        try {
            System.out.println("Type region id");
            id = MainView.getReader().readLine();
            regionController.deleteById(Long.parseLong(id));
            System.out.println("Region with id = " + id + " was successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Region with id = " + id + " wasn't deleted");
        }
    }

    private void saveAndPrint() {
        String name = null;
        try {
            System.out.println("Type name");
            name = MainView.getReader().readLine();
            Region region = regionController.save(new Region(name));
            System.out.println("New region with name " + region.getName() + " id = " + region.getId() + " was successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("New region with name = " + name + " wasn't saved");
        }
    }

    private void updateAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            System.out.println("Type new name");
            String name = MainView.getReader().readLine();
            regionController.update(new Region(Long.parseLong(id),
                    name));
            System.out.println("Region with id = " + id + " was updated");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Region with id = " + id + " wasn't updated");
        }
    }

    private void getOneAndPrint() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            Optional<Region> region = regionController.getById(Long.parseLong(id));
            if (region.isPresent()) {
                System.out.println(region);
            } else {
                System.out.println("Region with id = " + id + " wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Region with id = " + id + " wasn't found");
        }
    }

    private void getAllAndPrint() {
        List<Region> regions = regionController.getAll();
        if (regions.isEmpty()) {
            System.out.println("Regions list is empty");
        } else {
            System.out.println("Regions:");
            regions.stream()
                    .forEach(System.out::println);
        }
    }

    public void execute(Command command) {
        switch (command) {
            case DELETE_BY_ID:
                deleteAndPrint();
                return;
            case SAVE:
                saveAndPrint();
                return;
            case UPDATE:
                updateAndPrint();
                return;
            case GET_BY_ID:
                getOneAndPrint();
                return;
            case GET_ALL:
                getAllAndPrint();
                return;
            default:
                throw new RuntimeException("Unknown operation: " + command);
        }
    }
}
