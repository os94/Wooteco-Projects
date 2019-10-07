package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

    private final Reflections reflections;
    private final Map<Class<?>, Object> instances;

    public ControllerScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
        this.instances = new HashMap<>();
        setInstances();
    }

    private void setInstances() {
        reflections.getTypesAnnotatedWith(Controller.class)
                .forEach(clazz -> instances.put(clazz, instantiate(clazz)));
    }

    private Object instantiate(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("error occurred during scanning controllers", e);
            throw new RuntimeException(e);
        }
    }

    public Set<Method> getRequestMappingMethods() {
        return instances.keySet().stream()
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class)))
                .collect(Collectors.toSet());
    }

    public Object getInstanceFrom(Method method) {
        return instances.get(method.getDeclaringClass());
    }
}
