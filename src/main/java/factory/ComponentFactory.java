package factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

public class ComponentFactory {
    private static final Map<Class, Object> componentByClass = new HashMap<>();
    private static final String[] layouts = {"repository\\csv", "controller", "view\\impl"};

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
        return clazz.cast(componentByClass.get(getKeyBy(clazz)));
    }

    private static <T> void putClassAndInstance(Class<T> clazz) {
        if (clazz.getConstructors() != null && clazz.getConstructors().length != 0) {
            Constructor<?> mainConstructor = clazz.getConstructors()[0];
            if (mainConstructor.getParameterCount() == 0) {
                try {
                    componentByClass.putIfAbsent(getKeyBy(clazz), clazz.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                for (Class<?> parameterType : mainConstructor.getParameterTypes()) {
                    putClassAndInstance(parameterType);
                }
                Object[] objects = new Object[mainConstructor.getParameterCount()];
                for (int i = 0; i < mainConstructor.getParameterCount(); i++) {
                    objects[i] = componentByClass.get(mainConstructor.getParameterTypes()[i]);
                }
                try {
                    componentByClass.putIfAbsent(getKeyBy(clazz), clazz.getConstructor(mainConstructor.getParameterTypes())
                            .newInstance(objects));
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static <T> Class getKeyBy(Class<T> clazz) {
        return clazz.getInterfaces().length == 0 ? clazz : clazz.getInterfaces()[0];
    }

    public static List<Class> getClasses(ClassLoader cl, String currentPackage) {
        List<Class> classes = new ArrayList<>();
        URL upackage = cl.getResource(currentPackage);
        String dottedCurrentPackage = currentPackage.replace('\\', '.');
        if (upackage != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) upackage.getContent()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.endsWith(".class") && !line.contains("$")) {
                        classes.add(Class.forName(dottedCurrentPackage + "." + line.substring(0, line.lastIndexOf('.'))));
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    private static void createAndPutComponentsFrom(List<Class> classes) {
        Collections.sort(classes, (o1, o2) -> o1.getConstructors()[0].getParameterCount() - o2.getConstructors()[0].getParameterCount());
        classes.forEach(ComponentFactory::putClassAndInstance);
    }
}
