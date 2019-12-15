package nextstep.di.factory;

import com.google.common.collect.Maps;
import nextstep.stereotype.Controller;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.*;

import static java.util.Objects.nonNull;

public class ClasspathBeanFactory {
    private final Set<Class<?>> preInstantiateBeans;
    private final Map<Class<?>, Object> beans = Maps.newHashMap();

    public ClasspathBeanFactory(Set<Class<?>> preInstantiateBeans) {
        this.preInstantiateBeans = preInstantiateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        preInstantiateBeans.forEach(this::instantiateClass);
    }

    private Object instantiateClass(Class<?> clazz) {
        if (beans.containsKey(clazz)) {
            return beans.get(clazz);
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (nonNull(injectedConstructor)) {
            return registerBean(clazz, injectedConstructor);
        }
        return registerBean(clazz);
    }

    private Object registerBean(Class<?> clazz, Constructor<?> injectedConstructor) {
        Object instance = instantiateByConstructor(injectedConstructor);
        beans.put(clazz, instance);
        return instance;
    }

    private Object registerBean(Class<?> clazz) {
        clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans);
        Object instance = BeanUtils.instantiateClass(clazz);
        beans.put(clazz, instance);
        return instance;
    }

    private Object instantiateByConstructor(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            args.add(getInstance(parameterType));
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }

    private Object getInstance(Class<?> parameterType) {
        if (beans.containsKey(parameterType)) {
            return beans.get(parameterType);
        }
        return instantiateClass(parameterType);
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = new HashMap<>();
        preInstantiateBeans.stream()
                .filter(clazz -> clazz.isAnnotationPresent(Controller.class))
                .forEach(clazz -> controllers.put(clazz, beans.get(clazz)));
        return controllers;
    }
}
