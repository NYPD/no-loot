package rip.noloot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    private static final Logger LOGGER = LogManager.getLogger(ApiController.class);

    @GetMapping(value = "/test")
    public int test() {
        LOGGER.info("This is an info message");
        return 69;
    }

}
