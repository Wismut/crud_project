import model.Region;
import repository.JavaIORegionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        JavaIORegionRepository regionRepository = new JavaIORegionRepository();
        System.out.println("Region with id = 1:");
        System.out.println(regionRepository.getById(1L));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        System.out.println("Region 'AL' was added:");
        System.out.println(regionRepository.save(new Region("AL")));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        System.out.println("Region 'AW' was added:");
        System.out.println(regionRepository.save(new Region("AW")));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        regionRepository.deleteBy(4L);
        System.out.println("Region with id = 4 was deleted");
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        System.out.println("Region with id = 3 before update:");
        System.out.println(regionRepository.getById(3L));
        System.out.println("Update with id = 3");
        regionRepository.update(new Region(3L, "RT"));
        System.out.println("Region with id = 3 after update:");
        System.out.println(regionRepository.getById(3L));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());

        Files.write(Paths.get("src/main/resources/files/regions.txt"), "1,UA\n2,YTR\n3,UK\n".getBytes());
    }
}
