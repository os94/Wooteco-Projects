package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.di.bean.factory.BeanFactory;
import nextstep.di.bean.scanner.ClasspathBeanScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import samples.User;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    public void setup() {
        ClasspathBeanScanner classpathBeanScanner = new ClasspathBeanScanner("samples");
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.addAllBeanDefinition(classpathBeanScanner.scan());
        beanFactory.initialize();

        handlerMapping = new AnnotationHandlerMapping(beanFactory);
        handlerMapping.initialize();
    }

    @Test
    public void create_find() throws Exception {
        User user = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        createUser(user);
        assertThat(DataBase.findUserById(user.getUserId())).isEqualTo(user);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
        request.setParameter("userId", user.getUserId());
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = (HandlerExecution) handlerMapping.getHandler(request);
        execution.handle(request, response);

        assertThat(request.getAttribute("user")).isEqualTo(user);
    }

    private void createUser(User user) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users");
        request.setParameter("userId", user.getUserId());
        request.setParameter("password", user.getPassword());
        request.setParameter("name", user.getName());
        request.setParameter("email", user.getEmail());
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = (HandlerExecution) handlerMapping.getHandler(request);
        execution.handle(request, response);
    }
}
