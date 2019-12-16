package nextstep.di.bean.definition;

import nextstep.di.bean.InitializeBeanException;
import nextstep.di.bean.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
            return getDefaultConstructor(clazz);
        }
        return injectedConstructor;
    }

    // TODO: 2019-12-16 Exception Wrapping & extract to MyReflectionUtils.class ?
    private static Constructor<?> getDefaultConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 0)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(clazz + "is Interface."));
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
