import model.Post;
import model.Region;
import model.User;
import repository.CrudRepository;
import repository.JavaIOPostRepository;
import repository.JavaIORegionRepository;
import repository.JavaIOUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        testRegions();

//        testPosts();

        testUsers();
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
        CrudRepository<Post> postRepository = new JavaIOPostRepository();
        System.out.println("Post with id = 1:");
        System.out.println(postRepository.getById(1L));
        System.out.println("All posts:");
        System.out.println(postRepository.getAll());
        System.out.println("Post with content 'content3453245' was added:");
        System.out.println(postRepository.save(new Post("content3453245", LocalDateTime.now(), LocalDateTime.now())));
        System.out.println("All posts:");
        System.out.println(postRepository.getAll());
        System.out.println("Post with content 'dfgvdgs' was added:");
        System.out.println(postRepository.save(new Post("dfgvdgs", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2))));
        System.out.println("All posts:");
        System.out.println(postRepository.getAll());
        postRepository.deleteBy(4L);
        System.out.println("Post with id = 4 was deleted");
        System.out.println("All posts:");
        System.out.println(postRepository.getAll());
        System.out.println("Post with id = 3 before update:");
        System.out.println(postRepository.getById(3L));
        System.out.println("Update with id = 3");
        postRepository.update(new Post(3L, "new content"));
        System.out.println("Post with id = 3 after update:");
        System.out.println(postRepository.getById(3L));
        System.out.println("All posts:");
        System.out.println(postRepository.getAll());

        Files.write(Paths.get("src/main/resources/files/posts.txt"),
                "1,content1,1587897426,1587897428\n2,content2,1587893426,1587893428\n3,content3,1587887426,1587887429\n".getBytes());
    }

    private static void testUsers() throws IOException {
        CrudRepository<User> userRepository = new JavaIOUserRepository();
        System.out.println("User with id = 1:");
        System.out.println(userRepository.getById(1L));
        System.out.println("All users:");
        System.out.println(userRepository.getAll());
        System.out.println("User ('fn', 'ln') was added:");
        System.out.println(userRepository.save(new User("fn", "ln", Collections.emptyList(), new Region(2L, ""))));
        System.out.println("All users:");
        System.out.println(userRepository.getAll());
        System.out.println("User () was added:");
        System.out.println(userRepository.save(new User("name_random", "smth", Collections.emptyList(), new Region(3L, "sdgve"))));
        System.out.println("All users:");
        System.out.println(userRepository.getAll());
        userRepository.deleteBy(4L);
        System.out.println("User with id = 4 was deleted");
        System.out.println("All users:");
        System.out.println(userRepository.getAll());
        System.out.println("User with id = 3 before update:");
        System.out.println(userRepository.getById(3L));
        System.out.println("Update with id = 3");
        userRepository.update(new User(3L, "name", "surname", Collections.singletonList(new Post(1L, "")), new Region()));
        System.out.println("User with id = 3 after update:");
        System.out.println(userRepository.getById(3L));
        System.out.println("All users:");
        System.out.println(userRepository.getAll());

        Files.write(Paths.get("src/main/resources/files/users.txt"),
                "1,content1,1587897426,{1,3},1\n2,content2,1587893426,{1},2\n5,fn,ln,{3},1\n6,name_random,smth,{2},3\n".getBytes());
    }
}
