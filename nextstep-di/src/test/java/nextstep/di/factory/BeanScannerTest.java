package nextstep.di.factory;

import com.google.common.collect.Sets;
import nextstep.di.factory.example.JdbcQuestionRepository;
import nextstep.di.factory.example.JdbcUserRepository;
import nextstep.di.factory.example.MyQnaService;
import nextstep.di.factory.example.QnaController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BeanScannerTest {
    @Test
    @DisplayName("@Controller, @Service, @Repository Bean을 정상적으로 스캔하는지 확인")
    void getPreInstantiateClass() {
        Set<Class<?>> preInstantiateBeans = new BeanScanner("nextstep.di.factory.example").getPreInstantiateClass();
        Set<Class<?>> actualBeans = Sets.newHashSet(
                QnaController.class, MyQnaService.class,
                JdbcUserRepository.class, JdbcQuestionRepository.class);

        assertNotNull(preInstantiateBeans);
        assertThat(preInstantiateBeans).isEqualTo(actualBeans);
    }
}
