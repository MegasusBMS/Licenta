package app.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.entities.user.User;
import app.entities.user.UserService;
import app.entities.user.dt.UserSignIn;

@Controller
@RequestMapping("signin")
public class SignInController {

    private UserService userService;

    private SignInController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public void getSignInPage(){}

    @PostMapping
    public ModelAndView signIn(@ModelAttribute UserSignIn user){
        ModelAndView mav = new ModelAndView("signin");

        long cnp = 0;

        try{
            cnp = Long.parseLong(user.cnp());
        }catch(NumberFormatException e){
            return mav;
        }

        Optional<User> existingUser = userService.getUserByCNP(cnp);

        if(existingUser.isPresent())
            if(existingUser.get().userName().length()<=0){
                userService.updateUser(user);
                mav.setViewName("redirect:/login");
            }else{
                return mav;
            }

        if(userService.createUser(user)){
            mav.setViewName("redirect:/login");
        }
        return mav;
    }
}
