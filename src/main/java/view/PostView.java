package view;


import controller.PostController;

public class PostView implements View {
    private final PostController postController;
    private static PostView instance;

    public static PostView getInstance() {
        if (instance == null) {
            instance = new PostView(PostController.getInstance());
        }
        return instance;
    }

    public PostView(PostController postController) {
        this.postController = postController;
    }
}
