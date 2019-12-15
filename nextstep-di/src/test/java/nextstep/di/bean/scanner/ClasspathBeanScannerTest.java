package nextstep.di.bean.scanner;

import com.google.common.collect.Sets;
import nextstep.di.bean.example.JdbcQuestionRepository;
import nextstep.di.bean.example.JdbcUserRepository;
import nextstep.di.bean.example.MyQnaService;
import nextstep.di.bean.example.QnaController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClasspathBeanScannerTest {
    @Test
    @DisplayName("@Controller, @Service, @Repository Bean을 정상적으로 스캔하는지 확인")
    void getPreInstantiateClass() {
        Set<Class<?>> preInstantiateBeans = new ClasspathBeanScanner("nextstep.di.factory.example").getPreInstantiateClass();
        Set<Class<?>> actualBeans = Sets.newHashSet(
                QnaController.class, MyQnaService.class,
                JdbcUserRepository.class, JdbcQuestionRepository.class);

        assertNotNull(preInstantiateBeans);
        assertThat(preInstantiateBeans).isEqualTo(actualBeans);
    }
}
