package nextstep.di.bean.scanner;

import nextstep.di.bean.example.IntegrationConfig;
import nextstep.di.bean.example.MyJdbcTemplate;
import nextstep.di.bean.factory.BeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("@Configuration 설정파일에서 등록한 Bean을 가져오는지 확인")
    void scan_beans_by_configuration() {
        beanFactory.initialize();
        assertNotNull(beanFactory.getBean(DataSource.class));
    }

    @Test
    @DisplayName("ClasspathBeanScanner와 ConfigurationBeanScanner간에 Bean 주입이 되었는지 확인")
    void ClasspathBeanScanner_ConfigurationBeanScanner_통합() {
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
