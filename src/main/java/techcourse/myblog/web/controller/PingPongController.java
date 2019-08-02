package techcourse.myblog.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import techcourse.myblog.web.Ping;
import techcourse.myblog.web.Pong;

@RestController
public class PingPongController {
    /*@PostMapping("/ping")
    public String ping(@RequestBody String body) {
        return body;
    }*/

    @PostMapping("/ping")
    public Pong ping(@RequestBody Ping ping) {
        Pong pong = new Pong();
        pong.setPong(ping.getPing() + " good");
        return pong;
    }
}
