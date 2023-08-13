package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("home")

public class HomeController {

    @GetMapping
    public void getHomePage() {

    }

}

// @Controller
// @RequestMapping("/**")
// class RedirectHome {
//     @GetMapping
//     public RedirectView redirectToHome() {
//         // Redirect all non-registered roots to the home root ("/home")
//         return new RedirectView("/home", true);
//     }
// }
