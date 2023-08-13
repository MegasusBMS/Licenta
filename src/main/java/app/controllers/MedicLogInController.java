package app.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.entities.medic.Medic;
import app.entities.medic.MedicService;
import app.entities.medic.dt.MedicLogIn;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("app/medic/login")
public class MedicLogInController {

    private MedicService medicService;

    MedicLogInController(MedicService medicService) {
        this.medicService = medicService;
    }

    @GetMapping
    public void getMedicPage() {}

    @PostMapping
    public ModelAndView login(@ModelAttribute MedicLogIn medic, HttpServletResponse resp) {
        ModelAndView mav = new ModelAndView("redirect:/app/medic/login");
        Optional<Medic> serviceResp = medicService.LogInMedic(medic);

        if (serviceResp.isEmpty())
            return mav;

        Medic medicLogedIn = serviceResp.get();
        resp.addCookie(new Cookie("medicToken", medicLogedIn.getIdParafa() + ""));
        mav.setViewName("redirect:/app/medic");

        return mav;
    }
}
