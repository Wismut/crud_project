package controller;

import model.Region;
import repository.RegionRepository;

import java.util.List;
import java.util.Optional;

public class RegionController {
    private final RegionRepository regionRepository;

    public RegionController(RegionRepository regionRepository) {
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

    public Optional<Region> getById(Long id) {
        return regionRepository.getById(id);
    }
}
