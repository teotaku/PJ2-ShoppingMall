package supercoding.pj2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping("/")
    public String index() {
        return "쇼핑몰 2차 프로젝트1  INDEX화면. 스웨거 - > http://52.79.184.1:8080/swagger-ui/index.html";
    }
}
