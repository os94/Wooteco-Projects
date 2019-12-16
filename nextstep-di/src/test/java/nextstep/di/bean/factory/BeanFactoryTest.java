package nextstep.di.bean.factory;

import nextstep.di.bean.example.*;
import nextstep.di.bean.scanner.ClasspathBeanScanner;
import nextstep.stereotype.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeanFactoryTest {
    private BeanFactory beanFactory;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() {
        ClasspathBeanScanner classpathBeanScanner = new ClasspathBeanScanner("nextstep.di.bean.example");
        beanFactory = new BeanFactory();
        beanFactory.addAllBeanDefinition(classpathBeanScanner.scan());
        beanFactory.initialize();
    }

    @Test
    @DisplayName("해당 클래스와 의존하는 클래스에 대한 Bean들이 정상적으로 주입되었는지 확인")
    public void di() {
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
        Map<Class<?>, Object> controllers = beanFactory.getBeansAnnotatedWith(Controller.class);

        assertThat(controllers.containsKey(QnaController.class)).isTrue();
        assertThat(controllers.containsKey(MyQnaService.class)).isFalse();
        assertThat(controllers.containsKey(JdbcUserRepository.class)).isFalse();
    }
}
