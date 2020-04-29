package view;


import controller.RegionController;

public class RegionView implements View {
    private final RegionController regionController;

    public RegionView(RegionController regionController) {
        this.regionController = regionController;
    }
}
