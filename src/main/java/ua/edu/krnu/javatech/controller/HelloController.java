package ua.edu.krnu.javatech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public Map<String, String> hello(
            @RequestParam(defaultValue = "Student") String name
    ) {
        return Map.of("message", "Hello, " + name + "!");
    }
}