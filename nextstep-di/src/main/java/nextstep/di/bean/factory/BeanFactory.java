package nextstep.di.bean.factory;

import com.google.common.collect.Maps;
import nextstep.di.bean.definition.BeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private final Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();
    private final Map<Class<?>, Object> beans = Maps.newHashMap();

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void addBeanDefinition(BeanDefinition beanDefinition) {
        beanDefinitions.put(beanDefinition.getType(), beanDefinition);
    }

    public void addAllBeanDefinition(Set<BeanDefinition> newBeanDefinitions) {
        newBeanDefinitions.forEach(this::addBeanDefinition);
    }

    public void initialize() {
        beanDefinitions.keySet().forEach(clazz -> beans.putIfAbsent(clazz, createBean(clazz)));
        beanDefinitions.clear();
        log();
    }

    private Object createBean(Class<?> clazz) {
        // 이미 있으면 인스턴스 반환
        if (beans.containsKey(clazz)) {
            return beans.get(clazz);
        }

        // clazz가 interface인 경우, 구현Class를 찾아 인스턴스화, orElse
        clazz = BeanFactoryUtils.findConcreteClass2(clazz, beanDefinitions.keySet()).orElse(clazz);

        // (재귀를 통해) args들을 준비한뒤 인스턴스화
        BeanDefinition beanDefinition = beanDefinitions.get(clazz);

        Class<?>[] parameterTypes = beanDefinition.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (beans.containsKey(parameterType)) {
                args[i] = beans.get(parameterType);
                continue;
            }
            args[i] = createBean(parameterType);
        }
        Object instance = beanDefinition.instantiate(args);
        beans.put(beanDefinition.getType(), instance);
        return instance;
    }

    public Map<Class<?>, Object> getBeansAnnotatedWith(Class<? extends Annotation> annotation) {
        return beans.keySet().stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(toMap(identity(), beans::get));
    }

    private void log() {
        StringBuilder sb = new StringBuilder();
        beans.forEach((clazz, instance) -> sb.append(clazz).append(" : ").append(instance).append("\n"));
        logger.debug("Initialized Beans\n {}", sb.toString());
    }
}
