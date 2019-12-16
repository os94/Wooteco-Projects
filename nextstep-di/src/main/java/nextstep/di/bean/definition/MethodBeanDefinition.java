package nextstep.di.bean.definition;

import nextstep.di.bean.InitializeBeanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodBeanDefinition implements BeanDefinition {
    private static final Logger logger = LoggerFactory.getLogger(MethodBeanDefinition.class);

    private final Method method;
    private final Object instance;

    public MethodBeanDefinition(Method method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    @Override
    public Class<?> getType() {
        return method.getReturnType();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    @Override
    public Object instantiate(Object... params) {
        try {
            return method.invoke(instance, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("Error occurred while instantiating MethodBeanDefinition", e);
            throw new InitializeBeanException(e);
        }
    }
}
