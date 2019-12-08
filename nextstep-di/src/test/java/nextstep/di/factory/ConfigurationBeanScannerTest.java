package nextstep.di.factory;

import nextstep.di.factory.example.ExampleConfig;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigurationBeanScannerTest {
    @Test
    void register_simple() {
        ConfigurationBeanFactory beanFactory = new ConfigurationBeanFactory();
        ConfigurationBeanScanner beanScanner = new ConfigurationBeanScanner(beanFactory);

        beanScanner.register(ExampleConfig.class);
        beanFactory.initialize();

        assertNotNull(beanFactory.getBean(DataSource.class));
        System.out.println(beanFactory.getBean(DataSource.class));
    }
}
