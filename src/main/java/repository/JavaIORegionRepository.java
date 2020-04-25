package repository;


import model.Region;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaIORegionRepository {
    private final String REGION_REPOSITORY_PATH = "src/main/resources/files/regions.txt";
    private final String DELIMITER = ",";

    public Region getById(Long id) {
        Objects.requireNonNull(id);
        try {
            return getAll()
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
                    .map(s -> s.split(DELIMITER))
                    .map(s -> new Region(Long.parseLong(s[0]), s[1]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Region save(Region region) {
        Objects.requireNonNull(region);
        Long newId = getIdForNewRecord();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH), StandardOpenOption.APPEND)) {
            writer.append(String.valueOf(newId)).append(DELIMITER).append(region.getName());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        region.setId(newId);
        return region;
    }

    private Long getIdForNewRecord() {
        return getAll()
                .stream()
                .map(Region::getId)
                .max(Long::compare)
                .map(x -> x + 1)
                .orElse(1L);
    }

    public void deleteBy(Long id) {
        Objects.requireNonNull(id);
        List<Region> filteredRegions = getAll()
                .stream()
                .filter(r -> !id.equals(r.getId()))
                .collect(Collectors.toList());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH))) {
            for (Region region : filteredRegions) {
                writer.append(String.valueOf(region.getId())).append(DELIMITER).append(region.getName());
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Region update(Region region) {
        Objects.requireNonNull(region);
        Objects.requireNonNull(region.getId());
        List<Region> allRegions = getAll();
        Stream<Region> streamWithCurrentRegion = allRegions
                .stream()
                .filter(r -> region.getId().equals(r.getId()))
                .map(r -> region);
        Stream<Region> streamWithoutCurrentRegion = allRegions
                .stream()
                .filter(r -> !region.getId().equals(r.getId()));
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH))) {
            String stringToWrite = Stream.concat(streamWithCurrentRegion, streamWithoutCurrentRegion)
                    .map(r -> (r.getId() + DELIMITER + r.getName() + System.lineSeparator()))
                    .reduce("", (a, b) -> a + b);
                writer.write(stringToWrite);
                writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }
}
