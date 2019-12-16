package nextstep.di.bean.scanner;

import nextstep.di.bean.example.IntegrationConfig;
import nextstep.di.bean.example.MyJdbcTemplate;
import nextstep.di.bean.factory.BeanFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigurationBeanScannerTest {
    private BeanFactory beanFactory = new BeanFactory();
    private ConfigurationBeanScanner configurationBeanScanner;

    @Test
    @DisplayName("@Configuration 설정파일에서 등록한 Bean을 가져오는지 확인")
    void scan_beans_by_configuration() {
        configurationBeanScanner = new ConfigurationBeanScanner(IntegrationConfig.class);
        beanFactory.addAllBeanDefinition(configurationBeanScanner.scan());
        beanFactory.initialize();
        assertNotNull(beanFactory.getBean(DataSource.class));
    }

    @Test
    @DisplayName("ComponentScan에 의한 ClasspathBeanScanner와 ConfigurationBeanScanner간에 Bean 주입 확인")
    void register_by_componentScan() {
        configurationBeanScanner = new ConfigurationBeanScanner(IntegrationConfig.class);
        beanFactory.addAllBeanDefinition(configurationBeanScanner.scan());
        ClasspathBeanScanner classpathBeanScanner = new ClasspathBeanScanner(configurationBeanScanner.getBasePackages());
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
