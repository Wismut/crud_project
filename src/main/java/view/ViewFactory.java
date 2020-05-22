package view;


import factory.ComponentFactory;
import view.impl.PostViewImpl;
import view.impl.RegionViewImpl;
import view.impl.UserViewImpl;

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
