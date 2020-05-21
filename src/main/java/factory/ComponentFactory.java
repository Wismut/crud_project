package factory;

import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {
    private static final Map<Class, Object> componentByClass = new HashMap<>();

//    clazz.getConstructor(PostRepository.class, RegionRepository.class)
//    .newInstance(PostRepository.class.cast(componentByClass.get(PostRepository.class)),
//    RegionRepository.class.cast(componentByClass.get(RegionRepository.class)))

    public static <T> T getBy(Class<T> clazz) {
        try {
            putConstructorParamsIfAbsent(clazz);
            return clazz.cast(componentByClass.get(clazz));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void putConstructorParamsIfAbsent(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        if (clazz.getConstructors().length != 0) {
            for (Class<?> parameterType : clazz.getConstructors()[0].getParameterTypes()) {
                putConstructorParamsIfAbsent(parameterType);
            }
            componentByClass.putIfAbsent(clazz, clazz.newInstance());
        }
    }
}
