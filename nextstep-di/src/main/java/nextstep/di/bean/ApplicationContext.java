package nextstep.di.bean;

import nextstep.di.bean.factory.BeanFactory;
import nextstep.di.bean.scanner.ClasspathBeanScanner;
import nextstep.di.bean.scanner.ConfigurationBeanScanner;

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
        // @ComponentScan 경로로부터 ClasspathBS 초기화, @Bean에서 ConfigurationBS 초기화

        beanFactory.addAllBeanDefinition(configurationBeanScanner.scan());
        beanFactory.initialize();
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
