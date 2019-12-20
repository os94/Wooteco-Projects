package nextstep.di.bean;

import nextstep.di.bean.factory.BeanFactory;
import nextstep.di.bean.scanner.ClasspathBeanScanner;
import nextstep.di.bean.scanner.ConfigurationBeanScanner;

import java.lang.annotation.Annotation;
import java.util.Map;

public class ApplicationContext {
    private final BeanFactory beanFactory = new BeanFactory();
    private ConfigurationBeanScanner configurationBeanScanner;
    private ClasspathBeanScanner classpathBeanScanner;

    public ApplicationContext(Object... basePackage) {
        classpathBeanScanner = new ClasspathBeanScanner(basePackage);
        beanFactory.addAllBeanDefinition(classpathBeanScanner.scan());
        beanFactory.initialize();
    }

    public ApplicationContext(Class<?> configurationClass) {
        configurationBeanScanner = new ConfigurationBeanScanner(configurationClass);
        beanFactory.addAllBeanDefinition(configurationBeanScanner.scan());
        classpathBeanScanner = new ClasspathBeanScanner(configurationBeanScanner.getBasePackages());
        beanFactory.addAllBeanDefinition(classpathBeanScanner.scan());
        beanFactory.initialize();
    }

    public Map<Class<?>, Object> getBeansAnnotatedWith(Class<? extends Annotation> annotation) {
        return beanFactory.getBeansAnnotatedWith(annotation);
    }
}
