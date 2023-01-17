package app.controllers;

import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.user.User;
import app.user.UserService;
import app.user.usertypes.UserLogIn;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(path = "login")
class LoginControl{

	@Autowired
    private UserService userService;

    @GetMapping
    public void getTemplate(){}

    @PostMapping
    public ModelAndView login(@ModelAttribute UserLogIn userLogIn,HttpServletResponse resp){
		User user = userService.userLogin(userLogIn);
		if(user.asUserToSend().getId()==null)
			return new ModelAndView("login");
		
		Cookie cookie = new Cookie("userId", user.asUserToSend().getId()+"");
		cookie.setMaxAge(60*60*24);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		resp.addCookie(cookie);

		return new ModelAndView("redirect:mainpage");

    }

}
