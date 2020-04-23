package repository;

import model.Region;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JavaIORegionRepository {
    private final String REGION_REPOSITORY_PATH = "files/regions.txt";

    public Region getById(Long id) {
        if (id == null) {
            return null;
        }
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(REGION_REPOSITORY_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                if (id.equals(Long.parseLong(split[0]))) {
                    return new Region(Long.parseLong(split[0]), split[1]);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public List<Region> getAll() {
        return null;
    }

    public Region save(Region region) {
        return null;
    }

    public void deleteBy(Long id) {

    }

    public Region update(Region region) {
        return null;
    }
}
