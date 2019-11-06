package nextstep.di.factory;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        preInstanticateBeans.forEach(this::jaegui);
    }

    public void jaegui(Class<?> clazz) {
        if (beans.containsKey(clazz)) {
            return;
        }

        if (clazz.isInterface()) {
            clazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
            try {
                Object o = clazz.getDeclaredConstructor().newInstance();
                beans.put(clazz, o);
                return;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor.getParameterTypes().length == 0) {
            beans.put(clazz, BeanUtils.instantiateClass(injectedConstructor));
            return;
        } else {
            List<Object> params = new ArrayList<>();
            for (Class<?> parameterType : injectedConstructor.getParameterTypes()) {
                jaegui(parameterType);
                Class<?> concreteClass = BeanFactoryUtils.findConcreteClass(parameterType, preInstanticateBeans);
                params.add(beans.get(concreteClass));
            }
            Object o = BeanUtils.instantiateClass(injectedConstructor, params.toArray());
            beans.put(clazz, o);
        }
    }
}
