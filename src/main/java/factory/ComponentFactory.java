package factory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {
    private static final Map<Class, Object> componentByClass = new HashMap<>();

    public static <T> T getBy(Class<T> clazz) {
        try {
            putConstructorParamsIfAbsent(clazz);
            return clazz.cast(componentByClass.get(clazz.getInterfaces()[0]));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void putConstructorParamsIfAbsent(Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (clazz.getConstructors() != null && clazz.getConstructors().length != 0) {
            if (clazz.getConstructors()[0].getParameterCount() == 0) {
                componentByClass.putIfAbsent(clazz.getInterfaces()[0], clazz.newInstance());
            } else {
                for (Class<?> parameterType : clazz.getConstructors()[0].getParameterTypes()) {
                    putConstructorParamsIfAbsent(parameterType);
                }
                Object[] objects = new Object[clazz.getConstructors()[0].getParameterCount()];
                for (int i = 0; i < clazz.getConstructors()[0].getParameterCount(); i++) {
                    objects[i] = componentByClass.get(clazz.getConstructors()[0].getParameterTypes()[i]);
                }
                componentByClass.putIfAbsent(clazz.getInterfaces()[0], clazz.getConstructor(clazz.getConstructors()[0].getParameterTypes())
                        .newInstance(objects));
            }
        }
    }
}
