package nextstep.di.bean.factory;

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
        // 이미 있으면 인스턴스 반환
        if (beans.containsKey(clazz)) {
            return beans.get(clazz);
        }
        // (재귀를 통해) args들을 준비한뒤 인스턴스화
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (nonNull(injectedConstructor)) {
            Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
            List<Object> args = new ArrayList<>();
            for (Class<?> parameterType : parameterTypes) {
                if (beans.containsKey(parameterType)) {
                    args.add(beans.get(parameterType));
                }
                args.add(instantiateClass(parameterType));
            }
            Object instance = BeanUtils.instantiateClass(injectedConstructor, args.toArray());
            beans.put(clazz, instance);
            return instance;
        }
        // clazz가 interface인 경우, 구현Class를 찾아 인스턴스화
        clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans);
        Object instance = BeanUtils.instantiateClass(clazz);
        beans.put(clazz, instance);
        return instance;
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = new HashMap<>();
        preInstantiateBeans.stream()
                .filter(clazz -> clazz.isAnnotationPresent(Controller.class))
                .forEach(clazz -> controllers.put(clazz, beans.get(clazz)));
        return controllers;
    }
}
