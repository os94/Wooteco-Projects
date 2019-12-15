package nextstep.di.bean.definition;

import nextstep.di.bean.InitializeBeanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ClasspathBeanDefinition implements BeanDefinition {
    private static final Logger logger = LoggerFactory.getLogger(ClasspathBeanDefinition.class);

    private final Class<?> clazz;
    private final Constructor<?> constructor;

    public ClasspathBeanDefinition(Class<?> clazz, Constructor<?> constructor) {
        this.clazz = clazz;
        this.constructor = constructor;
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
            logger.error("Error occurred while instantiating ClasspathBeanDefinition", e);
            throw new InitializeBeanException(e);
        }
    }
}
