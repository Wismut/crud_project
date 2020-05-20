package repository.csv;

import model.Region;
import repository.RegionRepository;

import java.util.List;
import java.util.Optional;

public class CsvRegionRepositoryImpl implements RegionRepository {
    private static final String REGION_REPOSITORY_PATH = CSV_REPOSITORY_PATH + "/regions.csv";

    @Override
    public Optional<Region> getById(Long id) {
        return null;
    }

    @Override
    public List<Region> getAll() {
        return null;
    }

    @Override
    public Region save(Region region) {
        return null;
    }

    @Override
    public void deleteBy(Long id) {

    }

    @Override
    public Region update(Region region) {
        return null;
    }
}
