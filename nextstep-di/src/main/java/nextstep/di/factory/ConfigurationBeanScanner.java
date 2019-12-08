package nextstep.di.factory;

import nextstep.annotation.Configuration;

public class ConfigurationBeanScanner {
    private ConfigurationBeanFactory beanFactory;

    public ConfigurationBeanScanner(ConfigurationBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void register(Class clazz) {
        if (clazz.isAnnotationPresent(Configuration.class)) {
            beanFactory.addPreInstantiateBeans(clazz);
        }
        // Decide throw Exception or Not
    }
}
