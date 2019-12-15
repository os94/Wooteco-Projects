package nextstep.di.bean.scanner;

import nextstep.annotation.Bean;
import nextstep.annotation.Configuration;
import nextstep.di.bean.definition.BeanDefinition;
import nextstep.di.bean.definition.ConfigurationBeanDefinition;
import nextstep.di.bean.factory.ConfigurationBeanFactory;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ConfigurationBeanScanner implements BeanScanner {
    private ConfigurationBeanFactory beanFactory;
    private Reflections reflections;

    public ConfigurationBeanScanner(Class<?> configurationClass) {
        this.reflections = new Reflections(configurationClass.getPackageName());
    }

    public ConfigurationBeanScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public ConfigurationBeanScanner(ConfigurationBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void register(Class clazz) {
        if (clazz.isAnnotationPresent(Configuration.class)) {
            beanFactory.addPreInstantiateBeans(clazz);
        }
        // Decide throw Exception or Not
    }

    // TODO: 2019-12-15 ComponentScan 기능 추가

    @Override
    public Set<BeanDefinition> scan() {
        /*
        todo : ConfigurationBeanDefinition에서 method.invoke에 사용되는 instance 그때그때 만들지않고 여기서 같은걸 넣어줘서 재사용하도록 리팩토링
        Set<Object> instances = new HashSet<>();
        => map(method -> new ConfigurationBeanDefinition(method, instance))
        */
        return reflections.getTypesAnnotatedWith(Configuration.class).stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .map(method -> new ConfigurationBeanDefinition(method.getClass(), method))
                .collect(toSet());
    }
}
