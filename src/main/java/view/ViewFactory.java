package view;


public class ViewFactory {
    public static View create(String type) {
        switch (type) {
            case View.USER_NUMBER:
            case View.USER_LETTER:
                return UserView.getInstance();
            case View.POST_NUMBER:
            case View.POST_LETTER:
                return PostView.getInstance();
            case View.REGION_NUMBER:
            case View.REGION_LETTER:
                return RegionView.getInstance();
            default:
                return null;
        }
    }
}
