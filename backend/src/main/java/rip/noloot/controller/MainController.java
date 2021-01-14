package rip.noloot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/**
 * Main non rest controller for the site. Should not really have any additional request mappings besides the catch-all
 * 
 * @author NYPD
 *
 */
public class MainController {

    /**
     * Controller that takes every request besides index.html or api/something and forwards it to root directory which Vue's
     * router will pick up and display the appropriate page.
     * 
     * @return String which Spring will know to forward the request to the root /
     */
    @RequestMapping(value = "{fakePathVariable:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        return "forward:/";
    }
}
