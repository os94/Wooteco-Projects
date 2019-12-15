package nextstep.di.bean.scanner;

import nextstep.annotation.Configuration;
import nextstep.di.bean.factory.ConfigurationBeanFactory;

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
