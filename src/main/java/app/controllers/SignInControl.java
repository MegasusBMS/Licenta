package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import app.user.UserService;
import app.user.usertypes.UserSignIn;

@Controller
@RequestMapping(path = "signin")
public class SignInControl {

    @Autowired
    private UserService userService;

    @GetMapping
    public void getTemplate(){}

    @PostMapping
    public RedirectView signIn(@ModelAttribute UserSignIn user){
        System.out.print(user);
        if(userService.SignIn(user))
            return new RedirectView("login");
        return new RedirectView("signin");
    }

}