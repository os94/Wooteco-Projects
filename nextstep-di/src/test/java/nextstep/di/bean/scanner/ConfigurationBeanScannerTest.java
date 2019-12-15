package nextstep.di.bean.scanner;

import nextstep.di.bean.example.IntegrationConfig;
import nextstep.di.bean.example.MyJdbcTemplate;
import nextstep.di.bean.factory.BeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigurationBeanScannerTest {
    private BeanFactory beanFactory = new BeanFactory();
    private ConfigurationBeanScanner configurationBeanScanner;

    @BeforeEach
    void setUp() {
        configurationBeanScanner = new ConfigurationBeanScanner(IntegrationConfig.class);
        beanFactory.addAllBeanDefinition(configurationBeanScanner.scan());
    }

    @Test
    void register_simple() {
        beanFactory.initialize();
        assertNotNull(beanFactory.getBean(DataSource.class));
    }

    @Test
    void register_통합() {
        ClasspathBeanScanner classpathBeanScanner = new ClasspathBeanScanner("di.bean.example");
        beanFactory.addAllBeanDefinition(classpathBeanScanner.scan());

        beanFactory.initialize();

        DataSource datasourceBean = beanFactory.getBean(DataSource.class);
        assertNotNull(datasourceBean);

        MyJdbcTemplate myJdbcTemplateBean = beanFactory.getBean(MyJdbcTemplate.class);
        assertNotNull(myJdbcTemplateBean);

        DataSource dataSourceFromBean = myJdbcTemplateBean.getDataSource();
        assertNotNull(dataSourceFromBean);
        assertThat(datasourceBean).isEqualTo(dataSourceFromBean);
    }
}
