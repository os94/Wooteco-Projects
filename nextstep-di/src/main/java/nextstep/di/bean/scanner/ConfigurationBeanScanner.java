package nextstep.di.bean.scanner;

import nextstep.annotation.Bean;
import nextstep.annotation.ComponentScan;
import nextstep.annotation.Configuration;
import nextstep.di.bean.InitializeBeanException;
import nextstep.di.bean.definition.BeanDefinition;
import nextstep.di.bean.definition.MethodBeanDefinition;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ConfigurationBeanScanner implements BeanScanner {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationBeanScanner.class);

    private Reflections reflections;
    private String[] basePackages;

    public ConfigurationBeanScanner(Class<?> configurationClass) {
        this.reflections = new Reflections(configurationClass);
    }

    public ConfigurationBeanScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    @Override
    public Set<BeanDefinition> scan() {
        registerBasePackagesByComponentScan();

        Map<Method, Object> instances = new HashMap<>();
        return reflections.getTypesAnnotatedWith(Configuration.class).stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .map(method -> new MethodBeanDefinition(method, getInstance(instances, method)))
                .collect(toSet());
    }

    private void registerBasePackagesByComponentScan() {
        basePackages = reflections.getTypesAnnotatedWith(Configuration.class).stream()
                .filter(clazz -> clazz.isAnnotationPresent(ComponentScan.class))
                .findFirst()
                .map(clazz -> clazz.getAnnotation(ComponentScan.class))
                .map(ComponentScan::basePackages)
                .orElse(new String[]{});
    }

    private Object getInstance(Map<Method, Object> instances, Method method) {
        try {
            instances.putIfAbsent(method, method.getDeclaringClass().getDeclaredConstructor().newInstance());
            return instances.get(method);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Error occurred while getting Instance from Bean Method.", e);
            throw new InitializeBeanException(e);
        }
    }

    public String[] getBasePackages() {
        return basePackages;
    }
}
