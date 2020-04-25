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
        List<Region> allRegions = getAllRegions();
        Objects.requireNonNull(allRegions);
        Stream<Region> streamWithNewRegion = Collections.singletonList(region)
                .stream();
        Stream<Region> streamWithoutOldRegion = allRegions
                .stream()
                .filter(r -> !region.getId().equals(r.getId()));
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH))) {
            String stringToWrite = Stream.concat(streamWithNewRegion, streamWithoutOldRegion)
                    .map(r -> (r.getId() + DELIMITER + r.getName() + System.lineSeparator()))
                    .reduce("", (a, b) -> a + b);
            writer.write(stringToWrite);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }

    private List<Long> getAllIds() {
        return getAllRegions()
                .stream()
                .map(Region::getId)
                .collect(Collectors.toList());
    }
}
