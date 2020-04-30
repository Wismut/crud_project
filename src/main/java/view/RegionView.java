package view;


import command.Command;
import controller.RegionController;
import model.Region;

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
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Region with id = " + id + " wasn't deleted");
        }
    }

    private void save() {
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

    private void update() {
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

    private void getOne() {
        String id = null;
        try {
            System.out.println("Type id");
            id = MainView.getReader().readLine();
            Region region = regionController.getById(Long.parseLong(id));
            if (region != null) {
                System.out.println(region);
            } else {
                System.out.println("Region with id = " + id + " wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Region with id = " + id + " wasn't found");
        }
    }

    private void getAll() {
        System.out.println("Regions:");
        regionController.getAll().stream()
                .forEach(System.out::println);
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
