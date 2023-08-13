package app.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.statistics.Prognozare;
import app.statistics.StatisticContainer;
import app.statistics.StatisticService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("app/api/statistici")
@AllArgsConstructor
public class StatisticsRestController {

    StatisticService statisticService;

    @PostMapping
    public StatisticContainer postStatisticiAnimale(@RequestBody StatisticFilters filters) {

        return statisticService.StatisticRequest(filters.categorie(), filters.rase(), filters.specii(),
                filters.diagnostice(), filters.medicamente(), filters.specializari(), filters.medici(),
                filters.startDate(), filters.endDate(), filters.dateLocation());
    }

    @PostMapping("prognoza")
    public List<Double> postData(@RequestBody PrognozareObj pobj) {

        List<Double> prognoze = new ArrayList<>();

        for (LinkedHashMap<String, Double> map : pobj.data()) {
            Prognozare prognoza = new Prognozare(map, pobj.alpha());
            prognoze.add(prognoza.lunaUrmatoare());
        }

        return prognoze;
    }

}

record StatisticFilters(String categorie, List<String> rase, List<String> specii, List<String> diagnostice,
        List<String> medicamente, List<String> specializari, List<String> medici, String dateLocation,
        LocalDate startDate, LocalDate endDate) {
}

record PrognozareObj(double alpha, List<LinkedHashMap<String, Double>> data) {
}
