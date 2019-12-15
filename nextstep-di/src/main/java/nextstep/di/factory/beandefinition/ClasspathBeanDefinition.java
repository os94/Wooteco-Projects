package nextstep.di.factory.beandefinition;

public class ClasspathBeanDefinition implements BeanDefinition {
    @Override
    public Class<?> getType() {
        return null;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return new Class[0];
    }

    @Override
    public Object instantiate(Object... params) {
        return null;
    }
}
