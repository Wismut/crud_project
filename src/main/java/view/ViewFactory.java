package view;


import factory.ComponentFactory;
import view.implementation.PostViewImpl;
import view.implementation.RegionViewImpl;
import view.implementation.UserViewImpl;

public class ViewFactory {
    public static View create(String type) {
        switch (type) {
            case View.USER_NUMBER:
            case View.USER_LETTER:
                return ComponentFactory.getBy(UserViewImpl.class);
            case View.POST_NUMBER:
            case View.POST_LETTER:
                return ComponentFactory.getBy(PostViewImpl.class);
            case View.REGION_NUMBER:
            case View.REGION_LETTER:
                return ComponentFactory.getBy(RegionViewImpl.class);
            default:
                return null;
        }
    }
}
