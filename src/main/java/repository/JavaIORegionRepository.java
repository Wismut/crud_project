package repository;

import model.Region;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIORegionRepository {
    private final String REGION_REPOSITORY_PATH_WRITE = "src/main/resources/files/regions.txt";
    private final String REGION_REPOSITORY_PATH_READ = "files/regions.txt";
    private final String DELIMITER = ",";

    public Region getById(Long id) {
        if (id == null) {
            return null;
        }
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(REGION_REPOSITORY_PATH_READ);
             BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
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
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(REGION_REPOSITORY_PATH_READ);
             BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
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
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH_WRITE), StandardOpenOption.APPEND)) {
            writer.append(String.valueOf(region.getId())).append(",").append(region.getName()).append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }

    public void deleteBy(Long id) {
        if (id == null) {
            return;
        }
        List<Region> filteredRegions = getAll().stream().filter(r -> !id.equals(r.getId())).collect(Collectors.toList());
        System.out.println("filteredRegions: ");
        System.out.println(filteredRegions);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH_WRITE))) {
            for (Region region : filteredRegions) {
                writer.append(String.valueOf(region.getId())).append(",").append(region.getName()).append('\n');
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Region update(Region region) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REGION_REPOSITORY_PATH_WRITE), StandardOpenOption.APPEND)) {
            writer.append(String.valueOf(region.getId())).append(",").append(region.getName()).append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }
}
