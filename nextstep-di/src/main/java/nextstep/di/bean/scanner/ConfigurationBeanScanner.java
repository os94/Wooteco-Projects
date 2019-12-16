package nextstep.di.bean.scanner;

import nextstep.annotation.Bean;
import nextstep.annotation.ComponentScan;
import nextstep.annotation.Configuration;
import nextstep.di.bean.definition.BeanDefinition;
import nextstep.di.bean.definition.MethodBeanDefinition;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ConfigurationBeanScanner implements BeanScanner {
    private Reflections reflections;
    private String[] basePackages;

    public ConfigurationBeanScanner(Class<?> configurationClass) {
        this.reflections = new Reflections(configurationClass);
    }

    public ConfigurationBeanScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    @Override
    public Set<BeanDefinition> scan() {
        registerBasePackagesByComponentScan();
        /*
        todo : MethodBeanDefinition에서 method.invoke에 사용되는 instance 그때그때 만들지않고 여기서 같은걸 넣어줘서 재사용하도록 리팩토링
        Set<Object> instances = new HashSet<>();
        => map(method -> new MethodBeanDefinition(method, instance))
        */
        return reflections.getTypesAnnotatedWith(Configuration.class).stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .map(method -> new MethodBeanDefinition(method))
                .collect(toSet());
    }

    private void registerBasePackagesByComponentScan() {
        basePackages = reflections.getTypesAnnotatedWith(Configuration.class).stream()
                .filter(clazz -> clazz.isAnnotationPresent(ComponentScan.class))
                .findFirst()
                .map(clazz -> clazz.getAnnotation(ComponentScan.class))
                .map(ComponentScan::basePackages)
                .orElse(new String[]{});
    }

    public String[] getBasePackages() {
        return basePackages;
    }
}
