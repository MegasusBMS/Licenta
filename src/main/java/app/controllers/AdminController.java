package app.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
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
import app.entities.animal.dt.AnimalTable;
import app.entities.categorie.CategorieAnimal;
import app.entities.categorie.CategorieAnimalService;
import app.entities.categorie.dt.CategorieAnimalTable;
import app.entities.categorieDiagnostic.CategorieDiagnostic;
import app.entities.categorieDiagnostic.CategorieDiagnosticService;
import app.entities.categorieDiagnostic.dt.CategorieDiagnosticTable;
import app.entities.categorieMedicament.CategorieMedicament;
import app.entities.categorieMedicament.CategorieMedicamentService;
import app.entities.categorieMedicament.dt.CategorieMedicamentTable;
import app.entities.diagnostic.Diagnostic;
import app.entities.diagnostic.DiagnosticService;
import app.entities.diagnostic.dt.DiagnosticInput;
import app.entities.diagnostic.dt.DiagnosticTable;
import app.entities.medic.Medic;
import app.entities.medic.MedicService;
import app.entities.medic.dt.MedicSignIn;
import app.entities.medic.dt.MedicTable;
import app.entities.medicament.Medicament;
import app.entities.medicament.MedicamentService;
import app.entities.medicament.dt.MedicamentInput;
import app.entities.medicament.dt.MedicamentTable;
import app.entities.rasa.RasaAnimal;
import app.entities.rasa.RasaAnimalService;
import app.entities.rasa.dt.RasaAnimalInput;
import app.entities.rasa.dt.RasaAnimalTable;
import app.entities.specializare.Specializare;
import app.entities.specializare.SpecializareService;
import app.entities.specializare.dt.SpecializareTable;
import app.entities.tratament.Tratament;
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
@RequestMapping("app/admin")
public class AdminController {

    MedicService medicService;
    AnimalService animalService;
    MedicamentService medicamentService;
    TratamentService tratamentService;
    UserService userService;
    CategorieAnimalService categorieAnimalService;
    RasaAnimalService rasaAnimalService;
    SpecializareService specializareService;
    DiagnosticService diagnosticService;
    CategorieDiagnosticService categorieDiagnosticService;
    CategorieMedicamentService categorieMedicamentService;

    @GetMapping
    public ModelAndView getAdminPage(@CookieValue(name = "token", required = false) String token) {

        if (token == null)
            return new ModelAndView("redirect:../app/login");

        Optional<User> serviceResp = userService.getUserByUUID(token);

        if (serviceResp.get().getCnp() != 0)
            return new ModelAndView("redirect:../app/login");

        return new ModelAndView("admin");
    }

    @PostMapping
    public ModelAndView registerMedic(@ModelAttribute MedicSignIn medic) {
        medicService.createMedicIfNotExist(medic);
        return new ModelAndView("admin");
    }

    @GetMapping("/animale/{page}")
    public ModelAndView getAnimale(@PathVariable int page) {
        ModelAndView mav = new ModelAndView("admin");
        Page<Animal> animalPage = animalService.getAnimalPage(page);
        mav.addObject("animale", animalPage.map(Animal::toAnimalTable));
        mav.addObject("page", page);
        mav.addObject("maxPage", animalPage.getTotalPages());
        return mav;
    }

    @GetMapping("/diagnostice/{page}")
    public ModelAndView getDiagnostice(@PathVariable int page) {
        ModelAndView mav = new ModelAndView("admin");
        Page<Diagnostic> diagnosticPage = diagnosticService.getDiagnosticePage(page);
        mav.addObject("diagnostice", diagnosticPage.map(Diagnostic::toDiagnosticTable));
        mav.addObject("page", page);
        mav.addObject("maxPage", diagnosticPage.getTotalPages());
        return mav;
    }

    @GetMapping("/medici/{page}")
    public ModelAndView getMedici(@PathVariable int page) {
        ModelAndView mav = new ModelAndView("admin");
        Page<Medic> medicPage = medicService.getMedicPage(page);
        mav.addObject("medici", medicPage.map(Medic::toMedicTable));
        mav.addObject("page", page);
        mav.addObject("maxPage", medicPage.getTotalPages());
        return mav;
    }

    @GetMapping("/medicamente/{page}")
    public ModelAndView getMedicamente(@PathVariable int page) {
        ModelAndView mav = new ModelAndView("admin");
        Page<Medicament> medicamentPage = medicamentService.getMedicamentPage(page);
        mav.addObject("medicamente", medicamentPage.map(Medicament::toMedicamentTable));
        mav.addObject("page", page);
        mav.addObject("maxPage", medicamentPage.getTotalPages());
        return mav;
    }

    @GetMapping("/tratamente/{page}")
    public ModelAndView getTratamente(@PathVariable int page) {
        ModelAndView mav = new ModelAndView("admin");
        Page<Tratament> tratamentPage = tratamentService.getTratamentPage(page);
        mav.addObject("tratamente", tratamentPage.map(Tratament::toTratamentTable));
        mav.addObject("page", page);
        mav.addObject("maxPage", tratamentPage.getTotalPages());
        return mav;
    }

    @GetMapping("/utilizatori/{page}")
    public ModelAndView getUtilizatori(@PathVariable int page) {
        ModelAndView mav = new ModelAndView("admin");
        Page<User> userPage = userService.getUserPage(page);
        mav.addObject("utilizatori", userPage.map(User::toUserTable));
        mav.addObject("page", page);
        mav.addObject("maxPage", userPage.getTotalPages());
        return mav;
    }

    @GetMapping("/animale")
    public ModelAndView getAnimaleRedirect() {
        return new ModelAndView("redirect:./animale/0");
    }

    @GetMapping("/diagnostice")
    public ModelAndView getDiagnosticeRedirect() {
        return new ModelAndView("redirect:./diagnostice/0");
    }

    @GetMapping("/medici")
    public ModelAndView getMediciRedirect() {
        return new ModelAndView("redirect:./medici/0");
    }

    @GetMapping("/medicamente")
    public ModelAndView getMedicamenteRedirect() {
        return new ModelAndView("redirect:./medicamente/0");
    }

    @GetMapping("/tratamente")
    public ModelAndView getTratamenteRedirect() {
        return new ModelAndView("redirect:./tratamente/0");
    }

    @GetMapping("/utilizatori")
    public ModelAndView getUtilizatoriRedirect() {
        return new ModelAndView("redirect:./utilizatori/0");
    }

    @GetMapping("/edit/animal")
    public ModelAndView getAnimalRedirect() {
        return new ModelAndView("redirect:./animale/0");
    }

    @GetMapping("/edit/medic")
    public ModelAndView getMedicRedirect() {
        return new ModelAndView("redirect:./medici/0");
    }

    @GetMapping("/edit/medicament")
    public ModelAndView getMedicamentRedirect() {
        return new ModelAndView("redirect:./medicamente/0");
    }

    @GetMapping("/edit/tratament")
    public ModelAndView getTratamentRedirect() {
        return new ModelAndView("redirect:./tratamente/0");
    }

    @GetMapping("/edit/diagnostic")
    public ModelAndView getDiagnosticRedirect() {
        return new ModelAndView("redirect:./diagnostice/0");
    }

    @GetMapping("/edit/utilizator")
    public ModelAndView getUtilizatorRedirect() {
        return new ModelAndView("redirect:./utilizatori/0");
    }

    @GetMapping("/edit/animal/{id}")
    public ModelAndView getAnimal(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("formView");
        Optional<Animal> response = animalService.getAnimalById(id);
        if (response.isEmpty())
            return getAnimaleRedirect();
        Animal animal = response.get();

        LocalDate dataNastere = animal.getDataNastere().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

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

        mav.addObject("dataNastere", dataNastere);
        mav.addObject("animal", animal);
        mav.addObject("users", users);

        return mav;
    }

    @GetMapping("/edit/diagnostic/{id}")
    public ModelAndView getDiagnostic(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("formView");
        Optional<Diagnostic> response = diagnosticService.getDiagnosticById(id);
        if (response.isEmpty())
            return getAnimaleRedirect();
        Diagnostic diagnostic = response.get();
        mav.addObject("diagnostic", diagnostic);
        return mav;
    }

    @GetMapping("/edit/medic/{id}")
    public ModelAndView getMedic(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("formView");
        Optional<Medic> response = medicService.getMedicById(id);
        if (response.isEmpty())
            return getAnimaleRedirect();
        Medic medic = response.get();
        mav.addObject("medic", medic);
        mav.addObject("specializari", specializareService.getAllSpecializari());
        return mav;
    }

    @GetMapping("/edit/madicament/{id}")
    public ModelAndView getMedicament(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("formView");
        Optional<Medicament> response = medicamentService.getMedicamentById(id);
        if (response.isEmpty())
            return getAnimaleRedirect();
        Medicament medicament = response.get();
        mav.addObject("medicament", medicament);
        return mav;
    }

    @GetMapping("/edit/tratament/{id}")
    public ModelAndView getTratament(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("formView");
        Optional<Tratament> response = tratamentService.getTratamentById(id);
        if (response.isEmpty())
            return getAnimaleRedirect();
        Tratament tratament = response.get();
        mav.addObject("tratament", tratament);
        return mav;
    }

    @GetMapping("/edit/utilizator/{UUID}")
    public ModelAndView getUtilizator(@PathVariable String UUID) {
        ModelAndView mav = new ModelAndView("formView");
        Optional<User> response = userService.getUserByUUID(UUID);
        if (response.isEmpty())
            return getAnimaleRedirect();
        User user = response.get();
        mav.addObject("utilizator", user);
        return mav;
    }

    @GetMapping("/add/{type}")
    public ModelAndView getAddView(@PathVariable String type) {
        ModelAndView mav = new ModelAndView("formViewAdaugare");

        ObjectMapper objectMapper = new ObjectMapper();

        switch (type) {
            case "animal":

                List<CategorieAnimalTable> categorii = categorieAnimalService.getAllCategories().stream()
                        .map(CategorieAnimal::toCategorieAnimalTable).collect(Collectors.toList());
                List<RasaAnimalTable> rase = rasaAnimalService.getAllRase().stream().map(RasaAnimal::toRasaAnimalTable)
                        .collect(Collectors.toList());
                List<UserTable> users = userService.getAllUsers().stream().map(User::toUserTable)
                        .collect(Collectors.toList());

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

                break;
            case "medic":

                List<SpecializareTable> specializari = specializareService.getAllSpecializari().stream()
                        .map(Specializare::toSpecializareTable)
                        .collect(Collectors.toList());

                mav.addObject("specializari", specializari);
                break;
            case "diagnostic":

                List<AnimalTable> animale = animalService.getAllAnimals().stream().map(Animal::toAnimalTable)
                        .collect(Collectors.toList());

                List<CategorieDiagnosticTable> categoriiDiagnostice = categorieDiagnosticService
                        .getAllCategoriiDiagnostice().stream().map(CategorieDiagnostic::toCategorieDiagnosticTable)
                        .collect(Collectors.toList());

                mav.addObject("animale", animale);
                mav.addObject("categoriiDiagnostice", categoriiDiagnostice);
                break;
            case "medicament":

                List<CategorieMedicamentTable> categoriiMedicamente = categorieMedicamentService
                        .getAllCategoriiMedicamente().stream().map(CategorieMedicament::toCategorieAnimalTable)
                        .collect(Collectors.toList());

                mav.addObject("categoriiMedicamente", categoriiMedicamente);

                break;
            case "tratament":

                List<MedicamentTable> medicamente = medicamentService.getAllMedicamente().stream()
                        .map(Medicament::toMedicamentTable).collect(Collectors.toList());
                List<AnimalTable> animaleTratament = animalService.getAllAnimals().stream().map(Animal::toAnimalTable)
                        .collect(Collectors.toList());
                List<DiagnosticTable> diagnostice = diagnosticService.getAllDiagnostice().stream()
                        .map(Diagnostic::toDiagnosticTable).collect(Collectors.toList());
                List<MedicTable> medici = medicService.getAllMedici().stream().map(Medic::toMedicTable)
                        .collect(Collectors.toList());

                mav.addObject("animaleTratament", animaleTratament);

                mav.addObject("medicamente", medicamente);

                mav.addObject("medici", medici);

                String diagnosticeJson = "";
                try {
                    diagnosticeJson = objectMapper.writeValueAsString(diagnostice);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                mav.addObject("diagnostice", diagnosticeJson);

                break;
        }

        mav.addObject("type", type);
        return mav;
    }

    @PostMapping("/add/medic")
    public ModelAndView getAddView(@ModelAttribute MedicSignIn medic) {
        medicService.createMedicIfNotExist(medic);
        return new ModelAndView("redirect:../medici");
    }

    @PostMapping("/add/diagnostic")
    public ModelAndView getAddView(@ModelAttribute DiagnosticInput diagnostic) {
        diagnosticService.createDiagnostic(diagnostic);
        return new ModelAndView("redirect:../diagnostice");
    }

    @PostMapping("/add/animal")
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
            return new ModelAndView("redirect:../animale");
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

        if (rasa.isPresent())
            animalService.createAnimalIfNotExist(animal, rasa.get().getId());
        else
            animalService.createAnimalIfNotExist(animal);
        return new ModelAndView("redirect:../animale");
    }

    @PostMapping("/add/medicament")
    public ModelAndView getAddMedicament(@ModelAttribute MedicamentInput medicament) {
        medicamentService.createMedicamentIfNotExist(medicament);
        return new ModelAndView("redirect:../medicamente");
    }

    @PostMapping("/add/tratament")
    public ModelAndView getAddView(@ModelAttribute TratamentInput tratament) {
        tratamentService.createTratement(tratament);
        return new ModelAndView("redirect:../tratamente");
    }

    @PostMapping("/edit/medic/{id}")
    public ModelAndView editMedic(@ModelAttribute MedicSignIn newMedic, @PathVariable long id) {

        medicService.updateMedic(
                new Medic(id, newMedic.fullName(), Long.parseLong(newMedic.idParafa()), newMedic.password(),
                        specializareService.getSpecializareById(Long.parseLong(newMedic.idSpecializare())).get()));

        return new ModelAndView("redirect:../../medici");
    }

    @PostMapping("/edit/animal/{animalId}")
    public ModelAndView getEditAnimal(@ModelAttribute AnimalInput animal,
            @RequestParam(required = false, defaultValue = "") String newRasa,
            @RequestParam(required = false, defaultValue = "") String newCategorie,
            @RequestParam(required = false, defaultValue = "") String idCategorie,
            @PathVariable long animalId) {

        Optional<CategorieAnimal> categorie = Optional.empty();
        Optional<RasaAnimal> rasa = Optional.empty();

        Optional<User> user = Optional.empty();

        try {
            user = userService.getUserByCNP(Long.parseLong(animal.cnp()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ModelAndView("redirect:../../animale");
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
            animalService.update(animal, rasa.get().getId(), animalId);
        else
            animalService.update(animal, animalId);

        return new ModelAndView("redirect:../../animale");
    }

    @PostMapping("/edit/utilizator/{UUID}")
    public ModelAndView editUser(
            @ModelAttribute UserInput user,
            @RequestParam String UUID) {
        userService.update(new User(UUID, user.cnp(), user.userName(), user.password(), null));

        return new ModelAndView("redirect:../../utilizatori");
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ModelAndView("redirect:/home");
    }
}
