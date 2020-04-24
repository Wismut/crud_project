import model.Region;
import repository.JavaIORegionRepository;

public class Main {
    public static void main(String[] args) {
        JavaIORegionRepository regionRepository = new JavaIORegionRepository();
        System.out.println("Region with id = 1:");
        System.out.println(regionRepository.getById(1L));
        System.out.println("Region with id = 4 was added:");
        System.out.println(regionRepository.save(new Region(4L, "AL")));
        System.out.println("Region with id = 5 was added:");
        System.out.println(regionRepository.save(new Region(5L, "AL")));
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
        regionRepository.deleteBy(4L);
        System.out.println("Region with id = 4 was deleted");
        System.out.println("All regions:");
        System.out.println(regionRepository.getAll());
    }
}
