package nextstep.di.factory;

import nextstep.di.factory.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeanFactoryTest {
    private BeanFactory beanFactory;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() {
        Set<Class<?>> preInstanticateClazz = new ClasspathBeanScanner("nextstep.di.factory.example").getPreInstantiateClass();
        beanFactory = new BeanFactory(preInstanticateClazz);
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @Test
    @DisplayName("QuestionRepository가 Single Instance인지 확인")
    void check_QuestionRepository_single_instance() {
        MyQnaService myQnaService = beanFactory.getBean(MyQnaService.class);

        QuestionRepository actual = myQnaService.getQuestionRepository();
        QuestionRepository expected = beanFactory.getBean(JdbcQuestionRepository.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Bean들 중에서 Controller를 가져오는지 확인")
    void getControllers() {
        Map<Class<?>, Object> controllers = beanFactory.getControllers();

        assertThat(controllers.containsKey(QnaController.class)).isTrue();
        assertThat(controllers.containsKey(MyQnaService.class)).isFalse();
        assertThat(controllers.containsKey(JdbcUserRepository.class)).isFalse();
    }
}
