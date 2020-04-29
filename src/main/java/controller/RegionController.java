package controller;

import model.Region;
import repository.RegionRepository;
import repository.io.JavaIORegionRepository;

import java.util.List;

public class RegionController {
    private final RegionRepository regionRepository;
    private static RegionController instance;

    public static RegionController getInstance()  {
        if (instance == null) {
            instance = new RegionController(JavaIORegionRepository.getInstance());
        }
        return instance;
    }

    private RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Region save(Region region) {
        return regionRepository.save(region);
    }

    public Region update(Region region) {
        return regionRepository.update(region);
    }

    public void deleteById(Long id) {
        regionRepository.deleteBy(id);
    }

    public List<Region> getAll() {
        return regionRepository.getAll();
    }

    public Region getById(Long id) {
        return regionRepository.getById(id);
    }
}
