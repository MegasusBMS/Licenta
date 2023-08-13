package app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.entities.animal.Animal;
import app.entities.animal.AnimalService;
import app.entities.animal.dt.AnimalInput;
import app.entities.categorie.CategorieAnimal;
import app.entities.categorie.CategorieAnimalService;
import app.entities.categorie.dt.CategorieAnimalTable;
import app.entities.categorieDiagnostic.CategorieDiagnostic;
import app.entities.categorieDiagnostic.CategorieDiagnosticService;
import app.entities.categorieDiagnostic.dt.CategorieDiagnosticTable;
import app.entities.diagnostic.DiagnosticService;
import app.entities.diagnostic.dt.DiagnosticInput;
import app.entities.medic.Medic;
import app.entities.medic.MedicService;
import app.entities.medicament.Medicament;
import app.entities.medicament.MedicamentService;
import app.entities.medicament.dt.MedicamentTable;
import app.entities.rasa.RasaAnimal;
import app.entities.rasa.RasaAnimalService;
import app.entities.rasa.dt.RasaAnimalInput;
import app.entities.rasa.dt.RasaAnimalTable;
import app.entities.tratament.TratamentService;
import app.entities.tratament.dt.TratamentInput;
import app.entities.user.User;
import app.entities.user.UserService;
import app.entities.user.dt.UserInput;
import app.entities.user.dt.UserTable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("app/medic")
public class MedicViewController {

    MedicService medicService;
    AnimalService animalService;
    CategorieAnimalService categorieAnimalService;
    MedicamentService medicamentService;
    RasaAnimalService rasaAnimalService;
    TratamentService tratamentService;
    UserService userService;
    CategorieDiagnosticService categorieDiagnosticService;
    DiagnosticService diagnosticService;

    HashMap<Long, SearchElement> searchElementByMedicToken = new HashMap<>();

    @GetMapping
    public ModelAndView getMedicPage(@CookieValue(required = false) String medicToken) {
        ModelAndView mav = new ModelAndView("redirect:login");

        if (medicToken == null)
            return mav;

        Optional<Medic> serviceResp = Optional.empty();

        try {
            serviceResp = medicService.getMedicByIdParafa(Long.parseLong(medicToken));
        } catch (NumberFormatException e) {
            return mav;
        }

        if (serviceResp.isEmpty())
            return mav;

        mav.setViewName("medicView");

        String value = "";
        String by = "";

        if (searchElementByMedicToken.keySet().contains(Long.parseLong(medicToken))) {

            value = searchElementByMedicToken.get(Long.parseLong(medicToken)).searchValue();
            by = searchElementByMedicToken.get(Long.parseLong(medicToken)).searchBy();

        } else {

            SearchElement se = new SearchElement(value, by);
            searchElementByMedicToken.put(Long.parseLong(medicToken), se);

        }

        mav.setViewName("redirect:./medic/0");

        return mav;
    }

    @GetMapping("/{page}")
    public ModelAndView getMedicPage(@CookieValue(required = false) String medicToken, @PathVariable int page) {
        ModelAndView mav = new ModelAndView("redirect:login");

        if (medicToken == null)
            return mav;

        Optional<Medic> serviceResp = Optional.empty();

        try {
            serviceResp = medicService.getMedicByIdParafa(Long.parseLong(medicToken));
        } catch (NumberFormatException e) {
            return mav;
        }

        if (serviceResp.isEmpty())
            return mav;

        mav.setViewName("medicView");

        String value = "";
        String by = "";

        if (searchElementByMedicToken.keySet().contains(Long.parseLong(medicToken))) {

            value = searchElementByMedicToken.get(Long.parseLong(medicToken)).searchValue();
            by = searchElementByMedicToken.get(Long.parseLong(medicToken)).searchBy();

        } else {

            SearchElement se = new SearchElement(value, by);
            searchElementByMedicToken.put(Long.parseLong(medicToken), se);

        }

        Page<Animal> animale = animalService.getAllAnimalsBySearch(value, by, page);

        Medic m = serviceResp.get();
        ShowMedic medic = new ShowMedic(m.getFullName(), m.getIdParafa(), m.getSpecializare().getNumeSpecializare());

        mav.addObject("page", page);
        mav.addObject("maxPage", animale.getTotalPages());
        mav.addObject("animale", animale);
        mav.addObject("medic", medic);

        return mav;
    }

    @PostMapping("/{page}")
    public ModelAndView getAnimaleBySearchBarPage(@RequestParam String search, @RequestParam String searchBy,
            @CookieValue(required = false) String medicToken) {
        ModelAndView mav = new ModelAndView("redirect:login");

        if (medicToken == null)
            return mav;

        Optional<Medic> serviceResp = medicService.getMedicByIdParafa(Long.parseLong(medicToken));

        if (serviceResp.isEmpty())
            return mav;

        SearchElement se = new SearchElement(search, searchBy);
        searchElementByMedicToken.put(Long.parseLong(medicToken), se);

        mav.setViewName("redirect:./0");

        return mav;
    }

    @PostMapping()
    public ModelAndView getAnimaleBySearchBar(@RequestParam String search, @RequestParam String searchBy,
            @CookieValue(required = false) String medicToken) {
        ModelAndView mav = new ModelAndView("redirect:login");

        if (medicToken == null)
            return mav;

        Optional<Medic> serviceResp = medicService.getMedicByIdParafa(Long.parseLong(medicToken));

        if (serviceResp.isEmpty())
            return mav;

        SearchElement se = new SearchElement(search, searchBy);
        searchElementByMedicToken.put(Long.parseLong(medicToken), se);

        mav.setViewName("redirect:./medic/0");

        return mav;
    }

    @GetMapping("animal/{idAnimal}")
    public ModelAndView getAnimal(@PathVariable long idAnimal) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("animal", animalService.getAnimalById(idAnimal).get());

        mav.setViewName("medicAnimal");

        return mav;
    }

    @GetMapping("animal/{idAnimal}/fisaMedicala")
    public ModelAndView getFisaMedicala(@PathVariable long idAnimal) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("animal", animalService.getAnimalById(idAnimal).get());

        mav.setViewName("FisaMedicala");

        return mav;
    }

    @GetMapping("animal/{idAnimal}/diagnostic/{idDiagnostic}")
    public ModelAndView getAnimal(@PathVariable long idAnimal, @PathVariable long idDiagnostic) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("animal", animalService.getAnimalById(idAnimal).get());
        mav.addObject("diagnostic", diagnosticService.getDiagnosticById(idDiagnostic).get());

        mav.setViewName("medicDiagnostic");

        return mav;
    }

    @GetMapping("animal/{idAnimal}/addDiagnostic")
    public ModelAndView getAddDiangnostic(@PathVariable long idAnimal) {
        ModelAndView mav = new ModelAndView();

        List<CategorieDiagnosticTable> categoriiDiagnostice = categorieDiagnosticService.getAllCategoriiDiagnostice()
                .stream().map(CategorieDiagnostic::toCategorieDiagnosticTable).collect(Collectors.toList());

        mav.addObject("categoriiDiagnostice", categoriiDiagnostice);
        mav.addObject("idAnimal", idAnimal);

        mav.setViewName("MedicAddDiagnostic");

        return mav;
    }

    @GetMapping("animal/{idAnimal}/diagnostic/{idDiagnostic}/addTratament")
    public ModelAndView getAddTratament(@PathVariable long idAnimal, @PathVariable long idDiagnostic,
            @CookieValue(required = false) String medicToken) {
        ModelAndView mav = new ModelAndView();

        Optional<Medic> medic = medicService.getMedicFromCookie(medicToken);

        if (medic.isEmpty()) {
            mav.setViewName("redirect:../../../../login");
            return mav;
        }

        List<MedicamentTable> medicamente = medicamentService.getAllMedicamente().stream()
                .map(Medicament::toMedicamentTable).collect(Collectors.toList());

        mav.addObject("medicamente", medicamente);

        mav.setViewName("MedicAddTratament");

        return mav;
    }

    @PostMapping("animal/{idAnimal}/diagnostic/{idDiagnostic}/addTratament")
    public ModelAndView addTratament(@PathVariable long idAnimal, @PathVariable long idDiagnostic,
            @CookieValue(required = false) String medicToken, @RequestParam String listaMedicamente,
            @RequestParam String pretManopera) {
        ModelAndView mav = new ModelAndView();

        System.out.println(medicToken + " <<< " + listaMedicamente);

        Optional<Medic> medic = medicService.getMedicFromCookie(medicToken);

        if (medic.isEmpty()) {
            mav.setViewName("redirect:../../../../login");
            return mav;
        }

        System.out.println(medic.get().getFullName() + " <<< " + medic.get().getIdParafa() + " <<< ");

        tratamentService
                .createTratement(new TratamentInput(medic.get().getId(), idDiagnostic, listaMedicamente, pretManopera));
        mav.setViewName("redirect:../" + idDiagnostic);
        return mav;
    }

    @PostMapping("animal/{idAnimal}/addDiagnostic")
    public ModelAndView getAddView(@ModelAttribute DiagnosticInput diagnostic, @PathVariable long idAnimal) {
        diagnostic = new DiagnosticInput(diagnostic.numeDiagnostic(), diagnostic.specificatiiDiagnostic(),
                idAnimal + "", diagnostic.idCategorieD());
        diagnosticService.createDiagnostic(diagnostic);
        return new ModelAndView("redirect:../" + idAnimal);
    }

    @GetMapping("animal/{idAnimal}/diagnostic/{idDiagnostic}/fisaDiagnostic")
    public ModelAndView getFisaDiagnostic(@PathVariable long idAnimal, @PathVariable long idDiagnostic) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("animal", animalService.getAnimalById(idAnimal).get());
        mav.addObject("diagnostic", diagnosticService.getDiagnosticById(idDiagnostic).get());

        mav.setViewName("FisaDiagnostic");

        return mav;
    }

    @GetMapping("animal/{idAnimal}/diagnostic/{idDiagnostic}/tratament/{idTratament}/fisaTratament")
    public ModelAndView getAnimal(@PathVariable long idAnimal, @PathVariable long idDiagnostic,
            @PathVariable long idTratament) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("animal", animalService.getAnimalById(idAnimal).get());
        mav.addObject("tratament", tratamentService.getTratamentById(idTratament).get());

        mav.setViewName("fisaTratament");

        return mav;
    }

    @GetMapping("addAnimal")
    public ModelAndView getAddAnimal() {

        ModelAndView mav = new ModelAndView("MedicAddAnimal");

        List<CategorieAnimalTable> categorii = categorieAnimalService.getAllCategories().stream()
                .map(CategorieAnimal::toCategorieAnimalTable).collect(Collectors.toList());
        List<RasaAnimalTable> rase = rasaAnimalService.getAllRase().stream().map(RasaAnimal::toRasaAnimalTable)
                .collect(Collectors.toList());
        List<UserTable> users = userService.getAllUsers().stream().map(User::toUserTable)
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String categoriiJson = objectMapper.writeValueAsString(categorii);
            mav.addObject("categorii", categoriiJson);
        } catch (JsonProcessingException e) {
            mav.addObject("categorii", "");
            e.printStackTrace();
        }
        try {
            String raseJson = objectMapper.writeValueAsString(rase);
            mav.addObject("rase", raseJson);
        } catch (JsonProcessingException e) {
            mav.addObject("rase", "");
            e.printStackTrace();
        }
        mav.addObject("users", users);
        return mav;
    }

    @PostMapping("addAnimal")
    public ModelAndView getAddAnimalCategorieRasa(@ModelAttribute AnimalInput animal,
            @RequestParam(required = false, defaultValue = "") String newRasa,
            @RequestParam(required = false, defaultValue = "") String newCategorie,
            @RequestParam(required = false, defaultValue = "") String idCategorie) {

        Optional<CategorieAnimal> categorie = Optional.empty();
        Optional<RasaAnimal> rasa = Optional.empty();

        Optional<User> user = Optional.empty();

        try {
            user = userService.getUserByCNP(Long.parseLong(animal.cnp()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ModelAndView("redirect:../medic");
        }

        Optional<User> newUser = Optional.empty();

        if (user.isEmpty()) {
            newUser = userService.createUser(new UserInput(Long.parseLong(animal.cnp()), "", ""));
        }

        if (newCategorie.length() > 0) {
            categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal(newCategorie));
            categorie = categorieAnimalService.getCategorieAnimalByName(newCategorie);
        }

        if (newRasa.length() > 0) {

            String categorieId = idCategorie;

            if (categorie.isPresent())
                categorieId = categorie.get().getId() + "";

            rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput(newRasa, categorieId));
            rasa = rasaAnimalService.getRasaAnimalByName(newRasa);
        }

        if (newUser.isPresent())
            animal = new AnimalInput(animal.numeAnimal(), animal.dataNastere(), newUser.get().getCnp() + "",
                    animal.idRasa());

        if (rasa.isPresent())
            animalService.createAnimalIfNotExist(animal, rasa.get().getId());
        else
            animalService.createAnimalIfNotExist(animal);
        return new ModelAndView("redirect:../medic");
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("medicToken",null);
        cookie.setMaxAge(0);
        cookie.setPath("/app/medic");
        response.addCookie(cookie);

        return new ModelAndView("redirect:/home");
    }

    record SearchElement(String searchValue, String searchBy) {
    }

    record ShowMedic(String nume, long parafa, String specializare) {
    }
}
