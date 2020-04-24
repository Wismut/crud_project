package repository;

import model.Region;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JavaIORegionRepository {
    private final String REGION_REPOSITORY_PATH = "src/main/resources/files/regions.txt";
    private final String DELIMITER = ",";

    public Region getById(Long id) {
        Objects.requireNonNull(id);
        try (BufferedReader reader = new BufferedReader(new FileReader(REGION_REPOSITORY_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(DELIMITER);
                if (id.equals(Long.parseLong(split[0]))) {
                    return new Region(Long.parseLong(split[0]), split[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public List<Region> getAll() {
        List<Region> regions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(REGION_REPOSITORY_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(DELIMITER);
                regions.add(new Region(Long.parseLong(split[0]), split[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return regions;
        }
        return regions;
    }

    public Region save(Region region) {
        Objects.requireNonNull(region);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH), StandardOpenOption.APPEND)) {
            writer.append(String.valueOf(region.getId())).append(DELIMITER).append(region.getName());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }

    public void deleteBy(Long id) {
        Objects.requireNonNull(id);
        List<Region> filteredRegions = getAll().stream().filter(r -> !id.equals(r.getId())).collect(Collectors.toList());
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
        return save(region);
    }
}
