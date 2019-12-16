package nextstep.di.bean.scanner;

import com.google.common.collect.Sets;
import nextstep.di.bean.definition.BeanDefinition;
import nextstep.di.bean.example.JdbcQuestionRepository;
import nextstep.di.bean.example.JdbcUserRepository;
import nextstep.di.bean.example.MyQnaService;
import nextstep.di.bean.example.QnaController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

class ClasspathBeanScannerTest {
    @Test
    @DisplayName("@Controller, @Service, @Repository Bean을 정상적으로 스캔하는지 확인")
    void scan_beans_by_classpath() {
        Set<Class<?>> actualBeanTypes = Sets.newHashSet(
                QnaController.class, MyQnaService.class,
                JdbcUserRepository.class, JdbcQuestionRepository.class);

        Set<BeanDefinition> beanDefinitions = new ClasspathBeanScanner("nextstep.di.bean.example").scan();
        Set<Class<?>> collectedBeanTypes = beanDefinitions.stream()
                .map(beanDefinition -> beanDefinition.getType())
                .collect(toSet());

        assertThat(beanDefinitions).isNotEmpty();
        assertThat(collectedBeanTypes).isEqualTo(actualBeanTypes);
    }
}
