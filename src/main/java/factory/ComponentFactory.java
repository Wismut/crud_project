package factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {
    private static final Map<Class, Object> componentByClass = new HashMap<>();

    public static <T> T getBy(Class<T> clazz) {
        try {
            putConstructorParamsIfAbsent(clazz);
            return clazz.cast(componentByClass.get(getKeyBy(clazz)));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void putConstructorParamsIfAbsent(Class<T> clazz) throws ReflectiveOperationException {
        if (clazz.getConstructors() != null && clazz.getConstructors().length != 0) {
            Constructor<?> mainConstructor = clazz.getConstructors()[0];
            if (mainConstructor.getParameterCount() == 0) {
                componentByClass.putIfAbsent(getKeyBy(clazz), clazz.newInstance());
            } else {
                for (Class<?> parameterType : mainConstructor.getParameterTypes()) {
                    putConstructorParamsIfAbsent(parameterType);
                }
                Object[] objects = new Object[mainConstructor.getParameterCount()];
                for (int i = 0; i < mainConstructor.getParameterCount(); i++) {
                    objects[i] = componentByClass.get(mainConstructor.getParameterTypes()[i]);
                }
                componentByClass.putIfAbsent(getKeyBy(clazz), clazz.getConstructor(mainConstructor.getParameterTypes())
                        .newInstance(objects));
            }
        }
    }

    private static <T> Class getKeyBy(Class<T> clazz) {
        return clazz.getInterfaces()[0];
    }
}
