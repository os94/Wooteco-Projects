package nextstep.di.bean.definition;

public interface BeanDefinition {
    Class<?> getType();

    Class<?>[] getParameterTypes();

    Object instantiate(Object... params);
}
