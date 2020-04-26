import model.Post;
import model.Region;
import repository.CRUDRepository;
import repository.JavaIOPostRepository;
import repository.JavaIORegionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Main {
    public static void main(String[] args) throws IOException {
//        testRegions();

        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        Files.write(Paths.get("src/main/resources/files/posts.txt"),
                "1,content1,1587897426,1587897428\n2,content2,1587893426,1587893428\n3,content3,1587887426,1587887429\n".getBytes());
    }

    private static void testRegions() throws IOException {
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


    private static void testPosts() throws IOException {
        CRUDRepository<Post> regionRepository = new JavaIOPostRepository();
        System.out.println("Region with id = 1:");
        System.out.println(regionRepository.getById(1L));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        System.out.println("Region 'AL' was added:");
        System.out.println(regionRepository.save(new Post("AL", LocalDateTime.now(), LocalDateTime.MAX)));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        System.out.println("Region 'AW' was added:");
        System.out.println(regionRepository.save(new Post("AW", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2))));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        regionRepository.deleteBy(4L);
        System.out.println("Region with id = 4 was deleted");
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        System.out.println("Region with id = 3 before update:");
        System.out.println(regionRepository.getById(3L));
        System.out.println("Update with id = 3");
        regionRepository.update(new Post(3L, "new content"));
        System.out.println("Region with id = 3 after update:");
        System.out.println(regionRepository.getById(3L));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());

        Files.write(Paths.get("src/main/resources/files/regions.txt"), "1,UA\n2,YTR\n3,UK\n".getBytes());
    }
}
