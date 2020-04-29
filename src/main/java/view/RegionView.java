package view;


import controller.RegionController;
import model.Region;

import java.util.List;

public class RegionView extends View<Region,Long> {
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

    @Override
    void deleteById(Long id) {
        regionController.deleteById(id);
    }

    @Override
    Region save(Region region) {
        return regionController.save(region);
    }

    @Override
    Region update(Region region) {
        return regionController.update(region);
    }

    @Override
    Region getById(Long id) {
        return regionController.getById(id);
    }

    @Override
    List<Region> getAll() {
        return regionController.getAll();
    }
}
