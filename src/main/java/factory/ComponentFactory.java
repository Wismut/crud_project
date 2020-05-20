package factory;

import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {
    private static final Map<Class, Object> componentByClass = new HashMap<>();

    public static <T> T getBy(Class<T> clazz) {
        try {
            Object o = componentByClass.get(clazz);
            if (o == null) {
                componentByClass.put(clazz, clazz.newInstance());
                return clazz.cast(componentByClass.get(clazz));
            } else {
                return clazz.cast(o);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
