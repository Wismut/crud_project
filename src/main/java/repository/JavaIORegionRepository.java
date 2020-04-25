package repository;


import model.Region;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaIORegionRepository {
    private final String REGION_REPOSITORY_PATH = "src/main/resources/files/regions.txt";
    private final String DELIMITER = ",";

    public Region getById(Long id) {
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

    public List<Region> getAll() {
        return getAllRegions();
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

    public Region save(Region region) {
        Objects.requireNonNull(region);
        Long newId = createIdForNewRecord();
        region.setId(newId);
        writeToDatabase(Collections.singletonList(region), true);
        return region;
    }

    private Long createIdForNewRecord() {
        Optional<Long> result = getAllIds()
                .stream()
                .max(Long::compare);
        return result.isPresent() ? result.get() + 1 : 1L;
    }

    public void deleteBy(Long id) {
        Objects.requireNonNull(id);
        List<Region> filteredRegions = getAllRegions()
                .stream()
                .filter(r -> !id.equals(r.getId()))
                .collect(Collectors.toList());
        writeToDatabase(filteredRegions, false);
    }

    public Region update(Region region) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(region.getId());
        Stream<Region> streamWithNewRegion = Stream.of(region);
        Stream<Region> streamWithoutOldRegion = getAllRegions()
                .stream()
                .filter(r -> !region.getId().equals(r.getId()));
        List<Region> regions = Stream.concat(streamWithNewRegion, streamWithoutOldRegion)
                .collect(Collectors.toList());
        writeToDatabase(regions, false);
        return region;
    }

    private List<Long> getAllIds() {
        return getAllRegions()
                .stream()
                .map(Region::getId)
                .collect(Collectors.toList());
    }

    private void writeToDatabase(List<Region> regions, boolean isAppend) {
        try (BufferedWriter writer = isAppend ?
                Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH), StandardOpenOption.APPEND) :
                Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH))) {
            for (Region region : regions) {
                writer.write(region.getId() + DELIMITER + region.getName());
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
