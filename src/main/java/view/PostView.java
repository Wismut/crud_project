package view;


import controller.PostController;

public class PostView implements View {
    private final PostController postController;

    public PostView(PostController postController) {
        this.postController = postController;
    }
}
