package rip.noloot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "{fakePathVariable:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        return "forward:/";
    }
}
