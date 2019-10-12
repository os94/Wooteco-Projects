package nextstep.mvc.tobe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerExecutionTest {

    @Test
    void has_ResponseBodyAnnotation() throws Exception {
        HandlerExecution handlerExecutionWithResponseBody = new HandlerExecution(
                TestClass.class.getDeclaredConstructor().newInstance(),
                TestClass.class.getMethod("methodWithResponseBody"));
        assertThat(handlerExecutionWithResponseBody.hasResponseBodyAnnotation()).isTrue();
    }

    @Test
    void has_no_ResponseBodyAnnotation() throws Exception {
        HandlerExecution handlerExecutionWithOutResponseBody = new HandlerExecution(
                TestClass.class.getDeclaredConstructor().newInstance(),
                TestClass.class.getMethod("methodWithOutResponseBody"));
        assertThat(handlerExecutionWithOutResponseBody.hasResponseBodyAnnotation()).isFalse();
    }
}