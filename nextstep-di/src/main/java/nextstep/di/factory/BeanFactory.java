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

        // class에서 @Inject가 있는 생성자 찾기
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);

        // 잇으면 그 생성자로 인스턴스 생성후, Beans에 추가후 반환
        if (nonNull(injectedConstructor)) {
            Object instance = instantiateByConstructor(injectedConstructor);
            beans.put(clazz, instance);
            return instance;
        }
        // 없으면 기본 생성자로 인스턴스 생성후, Beans에 추가후 반환
        // Question: (UserRepo Interface, JdbcUserRepo Class Instance)처럼 빈 등록해도 되는지. UserRepo의 구현클래스가 하나라는 보장이 잇을까?
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstantiateBeans);
        Object instance = BeanUtils.instantiateClass(concreteClazz);
        beans.put(clazz, instance);
        return instance;
    }

    private Object instantiateByConstructor(Constructor<?> constructor) {
        // 인자로 넣을 Bean이 이미 잇으면 활용, 없으면 새로 생성
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> args = new ArrayList<>();
        for (Class<?> parameterType : parameterTypes) {
            if (beans.containsKey(parameterType)) {
                args.add(beans.get(parameterType));
                continue;
            }
            Object instance = instantiateClass(parameterType);
            args.add(instance);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
}
