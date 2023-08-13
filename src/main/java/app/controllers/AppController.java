package app.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.entities.animal.AnimalService;
import app.entities.user.User;
import app.entities.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("app")
public class AppController {

    UserService userService;
    AnimalService animalService;

    public AppController(UserService userService, AnimalService animalService) {
        this.userService = userService;
        this.animalService = animalService;
    }

    @GetMapping
    public ModelAndView getAppPage(@CookieValue(name = "token", required = false) String uuid) {
        ModelAndView mav = new ModelAndView("redirect:/login");

        if (uuid == null)
            return mav;

        Optional<User> serviceResp = userService.getUserByUUID(uuid);

        if (serviceResp.isEmpty())
            return mav;

        mav.setViewName("app");

        User user = serviceResp.get();
        mav.addObject("animals", animalService.getAnimalsOfUserAsDTO(user));
        mav.addObject("stapan", user.getUserName());

        return mav;
    }

    @GetMapping(value = "/{animalId}")
    public ModelAndView getAnimalPage(@CookieValue(name = "token", required = false) String token,
            @PathVariable String animalId) {
        ModelAndView mav = new ModelAndView("redirect:/login");

        System.out.println(animalId + "");

        if (token == null)
            return mav;

        long idAnimal = 0;
        try {
            idAnimal = Long.parseLong(animalId);
        } catch (NumberFormatException e) {
            return mav;
        }
        Optional<User> serviceResp = userService.getUserByUUID(token);

        if (serviceResp.isEmpty())
            return mav;

        User user = serviceResp.get();

        mav.setViewName("app");
        mav.addObject("animals", animalService.getAnimalsOfUserAsDTO(user));
        mav.addObject("animal", animalService.getAnimalById(idAnimal).get());
        mav.addObject("stapan", user.getUserName());
        return mav;
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ModelAndView("redirect:/home");
    }
}
