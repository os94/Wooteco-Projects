package nextstep.di.bean.factory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import nextstep.annotation.Bean;
import nextstep.di.bean.InitializeBeanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ConfigurationBeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationBeanFactory.class);

    private final Set<Class<?>> preInstantiateBeans = Sets.newHashSet();
    private final Map<Class<?>, Object> beans = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        preInstantiateBeans.forEach(this::instantiateClass);
    }

    private void instantiateClass(Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .forEach(this::registerBean);
    }

    private void registerBean(Method method) {
        if (beans.containsKey(method.getReturnType())) {
            return;
        }
        try {
            Object instance = method.invoke(getClassInstance(method));
            beans.put(method.getReturnType(), instance);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            logger.error("error occurred while initializing Bean", e);
            throw new InitializeBeanException(e);
        }
    }

    private Object getClassInstance(Method method)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return method.getDeclaringClass().getDeclaredConstructor().newInstance();
    }

    public void addPreInstantiateBeans(Class clazz) {
        preInstantiateBeans.add(clazz);
    }
}
