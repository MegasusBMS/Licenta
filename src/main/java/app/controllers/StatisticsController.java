package app.controllers;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.entities.categorie.CategorieAnimalService;
import app.entities.categorieDiagnostic.CategorieDiagnosticService;
import app.entities.medic.MedicService;
import app.entities.medicament.MedicamentService;
import app.entities.rasa.RasaAnimalService;
import app.entities.specializare.SpecializareService;
import app.statistics.StatisticContainer;
import app.statistics.StatisticService;
import app.utils.DecimalFormatter;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;

@Controller
@AllArgsConstructor
@RequestMapping("app/statistici")
public class StatisticsController {

    StatisticService statisticService;
    RasaAnimalService rasaAnimalService;
    CategorieAnimalService categorieAnimalService;
    CategorieDiagnosticService categorieDiagnosticService;
    MedicamentService medicamentService;
    SpecializareService specializareService;
    MedicService medicService;

    @GetMapping("animale")
    public synchronized ModelAndView getStatistics() {
        ModelAndView mav = new ModelAndView("app/statistics");

        LocalDate now = LocalDate.now();
        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());

        HashMap<String, Integer> animaleByCategorie = statisticService.mapByCategorii(firstDayOfYear, now);
        HashMap<String, Integer> animaleByVarsta = statisticService.mapByVarsta(firstDayOfYear, now);
        HashMap<String, HashMap<String, Integer>> animaleByRasa = statisticService.mapByRase(firstDayOfYear, now);

        String animaleByRasaJSON = new JSONObject(animaleByRasa).toJSONString();

        mav.addObject("now", now.plusDays(1));
        mav.addObject("firstDayOfYear", firstDayOfYear);
        mav.addObject("animaleByCategorie", animaleByCategorie);
        mav.addObject("animaleByVarsta", animaleByVarsta);
        mav.addObject("animaleByRasa", animaleByRasa);
        mav.addObject("animaleByRasaJSON", animaleByRasaJSON);

        return mav;
    }

    @PostMapping("animale")
    public ModelAndView updateStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate minDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate maxDate) {
        ModelAndView mav = new ModelAndView("app/statistics");

        HashMap<String, Integer> animaleByCategorie = statisticService.mapByCategorii(minDate, maxDate);
        HashMap<String, Integer> animaleByVarsta = statisticService.mapByVarsta(minDate, maxDate);
        HashMap<String, HashMap<String, Integer>> animaleByRasa = statisticService.mapByRase(minDate, maxDate);

        String animaleByRasaJSON = new JSONObject(animaleByRasa).toJSONString();

        LocalDate now = LocalDate.now();
        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());

        mav.addObject("now", now.plusDays(1));
        mav.addObject("firstDayOfYear", firstDayOfYear);
        mav.addObject("now", maxDate);
        mav.addObject("firstDayOfYear", minDate);
        mav.addObject("animaleByCategorie", animaleByCategorie);
        mav.addObject("animaleByVarsta", animaleByVarsta);
        mav.addObject("animaleByRasa", animaleByRasa);
        mav.addObject("animaleByRasaJSON", animaleByRasaJSON);

        return mav;
    }

    @GetMapping
    public ModelAndView getStatisticiAnimale() {

        ModelAndView mav = new ModelAndView("statisticiAnimale");

        LocalDate now = LocalDate.now();
        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());
        DecimalFormatter procentFormat = new DecimalFormatter("00.00%");
        DecimalFormatter curencyFormat = new DecimalFormatter("00.00RON");

        mav.addObject("now", now);
        mav.addObject("firstDayOfYear", firstDayOfYear);

        mav.addObject("rase", rasaAnimalService.getAllRase());
        mav.addObject("specii", categorieAnimalService.getAllCategories());
        mav.addObject("diagnostice", categorieDiagnosticService.getAllCategoriiDiagnostice());
        mav.addObject("medicamente", medicamentService.getAllMedicamente());
        mav.addObject("specializari", specializareService.getAllSpecializari());
        mav.addObject("medici", medicService.getAllMedici());
        mav.addObject("procentFormat", procentFormat);
        mav.addObject("curencyFormat", curencyFormat);

        StatisticContainer statistici = statisticService.StatisticRequest("categorieAnimal", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                firstDayOfYear, now.plusDays(1), "tratament");

        mav.addObject("statistici", statistici);

        return mav;
    }

    @PostMapping
    public ModelAndView postStatisticiAnimale(
            @RequestParam(required = false, defaultValue = "") String categorie,
            @RequestParam(required = false, defaultValue = "[]") List<String> rase,
            @RequestParam(required = false, defaultValue = "[]") List<String> specii,
            @RequestParam(required = false, defaultValue = "[]") List<String> diagnostice,
            @RequestParam(required = false, defaultValue = "[]") List<String> medicamente,
            @RequestParam(required = false, defaultValue = "[]") List<String> specializari,
            @RequestParam(required = false, defaultValue = "[]") List<String> medici,
            @RequestParam(required = false, defaultValue = "") String dateLocation,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        ModelAndView mav = new ModelAndView("statisticiAnimale");

        StatisticContainer statistici = statisticService.StatisticRequest(categorie, rase, specii, diagnostice,
                medicamente, specializari, medici, startDate, endDate, dateLocation);

        LocalDate now = LocalDate.now();
        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());

        DecimalFormatter procentFormat = new DecimalFormatter("00.00%");
        DecimalFormatter curencyFormat = new DecimalFormatter("#,##0.00RON");

        mav.addObject("now", now);
        mav.addObject("firstDayOfYear", firstDayOfYear);

        mav.addObject("rase", rasaAnimalService.getAllRase());
        mav.addObject("specii", categorieAnimalService.getAllCategories());
        mav.addObject("diagnostice", categorieDiagnosticService.getAllCategoriiDiagnostice());
        mav.addObject("medicamente", medicamentService.getAllMedicamente());
        mav.addObject("specializari", specializareService.getAllSpecializari());
        mav.addObject("medici", medicService.getAllMedici());
        mav.addObject("procentFormat", procentFormat);
        mav.addObject("curencyFormat", curencyFormat);

        mav.addObject("statistici", statistici);

        return mav;
    }
}
