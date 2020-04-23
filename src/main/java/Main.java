import repository.JavaIORegionRepository;

public class Main {
    public static void main(String[] args) {
        JavaIORegionRepository regionRepository = new JavaIORegionRepository();
        System.out.println(regionRepository.getById(1L));
    }
}
