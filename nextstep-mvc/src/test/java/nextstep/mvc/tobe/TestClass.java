package nextstep.mvc.tobe;

import nextstep.web.annotation.ResponseBody;

public class TestClass {
    public TestClass() {
    }

    public @ResponseBody
    User methodWithResponseBody() {
        return new User("sean", "1234", "os", "sean@sean.com");
    }

    public void methodWithOutResponseBody() {
    }
}
