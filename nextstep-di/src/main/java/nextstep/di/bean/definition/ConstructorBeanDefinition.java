package nextstep.di.bean.definition;

import nextstep.ReflectionUtils;
import nextstep.di.bean.InitializeBeanException;
import nextstep.di.bean.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorBeanDefinition implements BeanDefinition {
    private static final Logger logger = LoggerFactory.getLogger(ConstructorBeanDefinition.class);

    private final Class<?> clazz;
    private final Constructor<?> constructor;

    public ConstructorBeanDefinition(Class<?> clazz) {
        this.clazz = clazz;
        this.constructor = resolveConstructor(clazz);
    }

    private Constructor<?> resolveConstructor(Class<?> clazz) {
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor == null) {
            return ReflectionUtils.getDefaultConstructor(clazz);
        }
        return injectedConstructor;
    }

    @Override
    public Class<?> getType() {
        return clazz;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return constructor.getParameterTypes();
    }

    @Override
    public Object instantiate(Object... params) {
        try {
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Error occurred while instantiating ConstructorBeanDefinition", e);
            throw new InitializeBeanException(e);
        }
    }
}
