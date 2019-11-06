package nextstep.di.factory;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstantiateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstantiateBeans) {
        this.preInstantiateBeans = preInstantiateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        preInstantiateBeans.forEach(this::instantiateClass);
    }

    public Object instantiateClass(Class<?> clazz) {
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
        // Question: (UserRepo Interface, JdbcUserRepo Class Instance)처럼 빈 등록해도 되는지. UserRepo의 구현클래스가 하나라는 보장이 잇을까?
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans);
        Object instance = BeanUtils.instantiateClass(concreteClazz);
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
}
