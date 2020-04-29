package view;


import controller.UserController;

public class UserView implements View {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }
}
