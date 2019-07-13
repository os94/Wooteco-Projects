package techcourse.myblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
    @ResponseBody
    @GetMapping("/helloworld")
    public String helloworld(String blogName) {
        return blogName;
    }

    @ResponseBody
    @PostMapping("/helloworld")
    public String helloworld3(@RequestBody String body) {
        return body;
    }
}
