package view;


import controller.RegionController;

public class RegionView implements View {
    private final RegionController regionController;
    private static RegionView instance;

    public static RegionView getInstance() {
        if (instance == null) {
            instance = new RegionView(RegionController.getInstance());
        }
        return instance;
    }

    public RegionView(RegionController regionController) {
        this.regionController = regionController;
    }
}
