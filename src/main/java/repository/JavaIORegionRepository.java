package repository;


import model.Region;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class JavaIORegionRepository implements CrudRepository<Region> {
    private final String REGION_REPOSITORY_PATH = REPOSITORY_PATH + "/regions.txt";

    public Region getById(Long id) {
        return getRegionById(id);
    }

    public List<Region> getAll() {
        return getAllRegions();
    }

    public Region save(Region region) {
        Objects.requireNonNull(region);
        Long newId = createIdForNewRecord();
        region.setId(newId);
        writeToDatabase(Collections.singletonList(region), StandardOpenOption.APPEND);
        return region;
    }

    public void deleteBy(Long id) {
        Objects.requireNonNull(id);
        List<Region> filteredRegions = getAllRegions()
                .stream()
                .filter(r -> !id.equals(r.getId()))
                .collect(Collectors.toList());
        writeToDatabase(filteredRegions);
    }

    public Region update(Region region) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(region.getId());
        List<Region> allRegions = getAllRegions();
        if (getRegionById(region.getId()) == null) {
            return region;
        }
        List<Region> filteredRegions = allRegions
                .stream()
                .filter(r -> !region.getId().equals(r.getId()))
                .collect(Collectors.toList());
        filteredRegions.add(region);
        writeToDatabase(filteredRegions);
        return region;
    }

    private List<Long> getAllIds() {
        return getAllRegions()
                .stream()
                .map(Region::getId)
                .collect(Collectors.toList());
    }

    private void writeToDatabase(List<Region> regions, StandardOpenOption... openOptions) {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH), openOptions)) {
            for (Region region : regions) {
                writer.write(region.getId() + DELIMITER + region.getName());
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Region getRegionById(Long id) {
        Objects.requireNonNull(id);
        try {
            return getAllRegions()
                    .stream()
                    .filter(r -> id.equals(r.getId()))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Region> getAllRegions() {
        try {
            return Files.readAllLines(Paths.get(REGION_REPOSITORY_PATH))
                    .stream()
                    .map(s -> {
                        String[] split = s.split(DELIMITER);
                        return new Region(Long.parseLong(split[0]), split[1]);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Long createIdForNewRecord() {
        Optional<Long> result = getAllIds()
                .stream()
                .max(Long::compare);
        return result.map(x -> x + 1).orElse(1L);
    }
}
