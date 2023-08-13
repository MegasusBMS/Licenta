package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.entities.user.User;
import app.entities.user.UserService;
import app.entities.user.dt.UserLogIn;
import app.utils.UserValidation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("login")
public class LogInController {

    private UserService userService;

    LogInController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public void getLoginPage() {
    }

    @PostMapping
    public ModelAndView login(@ModelAttribute UserLogIn user, HttpServletResponse resp) {
        ModelAndView mav = new ModelAndView("login");

        UserValidation validation = new UserValidation(userService);
        validation.validate(user);

        if (!validation.getValidationResponse().isValid()) {
            System.out.println("userName: " + validation.getValidationResponse().userNameErrors());
            System.out.println(validation.getValidationResponse().passwordErrors());
            mav.addObject("userNameErrors", validation.getValidationResponse().userNameErrors());
            mav.addObject("passwordErrors", validation.getValidationResponse().passwordErrors());
            mav.addObject("returnedUserName", user.userName());
            mav.addObject("returnedPassword", user.password());
            return mav;
        }

        User userLogedIn = validation.getValidationResponse().getUser().get();

        Cookie cookie = new Cookie("token", userLogedIn.getUUID());
        resp.addCookie(cookie);

        if (userLogedIn.getCnp() == 0)
            mav.setViewName("redirect:/app/admin");
        else
            mav.setViewName("redirect:/app");
        return mav;
    }
}
