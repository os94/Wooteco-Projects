package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationHandlerMappingTest {
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    public void setup() {
        handlerMapping = new AnnotationHandlerMapping("nextstep.mvc.tobe");
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
        HandlerExecution execution = handlerMapping.getHandler(request);
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
        HandlerExecution execution = handlerMapping.getHandler(request);
        execution.handle(request, response);
    }

    @Test
    public void handle_all_methods() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/do-some");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(request);
        assertThat(execution).isNotNull();
        ModelAndView mav = execution.handle(request, response);
        assertThat(mav.getObject("result")).isEqualTo("ok");
    }

    @Test
    public void handle_multiple_methods() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("PUT", "/do-other");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecution execution = handlerMapping.getHandler(request);
        assertThat(execution).isNotNull();
        ModelAndView mav = execution.handle(request, response);
        assertThat(mav.getObject("result")).isEqualTo("ok");

        MockHttpServletRequest request2 = new MockHttpServletRequest("DELETE", "/do-other");
        MockHttpServletResponse response2 = new MockHttpServletResponse();
        HandlerExecution execution2 = handlerMapping.getHandler(request);
        assertThat(execution).isNotNull();
        ModelAndView mav2 = execution2.handle(request2, response2);
        assertThat(mav2.getObject("result")).isEqualTo("ok");
    }
}
