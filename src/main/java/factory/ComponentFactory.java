package factory;

import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

public class ComponentFactory {
    private static final Map<Class, Object> componentByClass = new HashMap<>();
    private static final String[] layouts = {"repository\\csv", "controller", "view"};

    static {
        try {
            for (String layout : layouts) {
                createAndPutComponentsFrom(getClasses(ComponentFactory.class.getClassLoader(), layout));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getBy(Class<T> clazz) {
//        putConstructorParamsIfAbsent(clazz);
        return clazz.cast(componentByClass.get(getKeyBy(clazz)));
    }

    private static <T> void putConstructorParamsIfAbsent(Class<T> clazz) {
        if (clazz.getConstructors() != null && clazz.getConstructors().length != 0) {
            Constructor<?> mainConstructor = clazz.getConstructors()[0];
            if (mainConstructor.getParameterCount() == 0) {
                try {
                    componentByClass.putIfAbsent(getKeyBy(clazz), clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                for (Class<?> parameterType : mainConstructor.getParameterTypes()) {
                    putConstructorParamsIfAbsent(parameterType);
                }
                Object[] objects = new Object[mainConstructor.getParameterCount()];
                for (int i = 0; i < mainConstructor.getParameterCount(); i++) {
                    objects[i] = componentByClass.get(mainConstructor.getParameterTypes()[i]);
                }
                try {
                    componentByClass.putIfAbsent(getKeyBy(clazz), clazz.getConstructor(mainConstructor.getParameterTypes())
                            .newInstance(objects));
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static <T> Class getKeyBy(Class<T> clazz) {
        return clazz.getInterfaces().length == 0 ? clazz : clazz.getInterfaces()[0];
    }

    public static List<Class> getClasses(ClassLoader cl, String currentPackage) throws Exception {
        List<Class> classes = new ArrayList<>();
        URL upackage = cl.getResource(currentPackage);
        String dottedCurrentPackage = currentPackage.replace('\\', '.');
        if (upackage != null) {
            DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
            String line;
            while ((line = dis.readLine()) != null) {
                if (line.endsWith(".class") && !line.contains("$")) {
                    classes.add(Class.forName(dottedCurrentPackage + "." + line.substring(0, line.lastIndexOf('.'))));
                }
            }
        }
        return classes;
    }

    private static void createAndPutComponentsFrom(List<Class> classes) {
        Collections.sort(classes, (o1, o2) -> o1.getConstructors()[0].getParameterCount() - o2.getConstructors()[0].getParameterCount());
        classes.forEach(ComponentFactory::putConstructorParamsIfAbsent);
    }
}
