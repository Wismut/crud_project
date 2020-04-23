import model.Region;
import repository.JavaIORegionRepository;

public class Main {
    public static void main(String[] args) {
        JavaIORegionRepository regionRepository = new JavaIORegionRepository();
        System.out.println(regionRepository.getById(1L));
        System.out.println(regionRepository.save(new Region(4L, "AL")));
        System.out.println(regionRepository.getAll());
        // TODO: 4/23/2020 Output is  [Region{id=1, name='UA'}, Region{id=2, name='YTR'}, Region{id=3, name='UK'}]
        // but must be 4 regions. Regions with id equals 4 is absent in the output but present in the file regions.txt
    }
}
