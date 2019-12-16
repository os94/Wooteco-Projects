package nextstep.di.bean.definition;

import nextstep.di.bean.InitializeBeanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodBeanDefinition implements BeanDefinition {
    private static final Logger logger = LoggerFactory.getLogger(MethodBeanDefinition.class);

    private final Class<?> clazz;
    private final Method method;
    //private final Object instance;

    public MethodBeanDefinition(Method method) {
        this.clazz = method.getReturnType();
        this.method = method;
    }

    @Override
    public Class<?> getType() {
        return clazz;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    @Override
    public Object instantiate(Object... params) {
        try {
            return method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance(), params);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            logger.error("Error occurred while instantiating MethodBeanDefinition", e);
            throw new InitializeBeanException(e);
        }
    }
}
