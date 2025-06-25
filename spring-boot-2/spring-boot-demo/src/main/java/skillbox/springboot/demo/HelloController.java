package skillbox.springboot.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import skillbox.springboot.demo.starter.MyService;

@RestController
public class HelloController {
    private final MyService myService;

    @Autowired
    public HelloController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/custom")
    public String customHello() {
        return myService.sayHello();
    }
} 