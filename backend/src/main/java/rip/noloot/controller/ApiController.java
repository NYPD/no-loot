package rip.noloot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    @GetMapping(value = "/test")
    public int test() {
        LOGGER.info("This is an info message");
        return 69;
    }

    //    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    //    // Required because of 'mode: history' usage in frontend routing, see README for further details
    //    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    //    public String redirectApi() {
    //        return "forward:/";
    //    }

}
