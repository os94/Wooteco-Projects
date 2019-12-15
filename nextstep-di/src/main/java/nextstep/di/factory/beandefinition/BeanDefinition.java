package nextstep.di.factory.beandefinition;

public interface BeanDefinition {
    Class<?> getType();

    Class<?>[] getParameterTypes();

    Object instantiate(Object... params);
}
