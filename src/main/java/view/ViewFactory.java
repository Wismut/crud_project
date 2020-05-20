package view;


import factory.ComponentFactory;

public class ViewFactory {
    public static View create(String type) {
        switch (type) {
            case View.USER_NUMBER:
            case View.USER_LETTER:
                return ComponentFactory.getBy(UserView.class);
            case View.POST_NUMBER:
            case View.POST_LETTER:
                return ComponentFactory.getBy(PostView.class);
            case View.REGION_NUMBER:
            case View.REGION_LETTER:
                return ComponentFactory.getBy(RegionView.class);
            default:
                throw new RuntimeException("Unknown view type: " + type);
        }
    }
}
