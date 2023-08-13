package app.statistics;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import app.entities.animal.Animal;
import app.entities.animal.AnimalService;
import app.entities.categorie.CategorieAnimal;
import app.entities.categorie.CategorieAnimalService;
import app.entities.diagnostic.Diagnostic;
import app.entities.rasa.RasaAnimal;
import app.entities.rasa.RasaAnimalService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StatisticService {

    AnimalService animalService;
    CategorieAnimalService categorieAnimalService;
    RasaAnimalService rasaAnimalService;
    StatisticRepo statisticRepo;

    public HashMap<String, Integer> mapByCategorii(LocalDate startDate, LocalDate endDate) {

        List<Animal> animale = animalService.getAllAnimals();
        List<CategorieAnimal> categorii = categorieAnimalService.getAllCategories();

        HashMap<String, Integer> mapareaAparitiilor = new HashMap<>();

        for (Animal animal : animale) {
            if (animal.getDiagnostice().size() < 1)
                continue;
            Date ultimaVizita = animal.getDiagnostice().get(0).getDataDiagnostic();
            for (Diagnostic diagnostic : animal.getDiagnostice()) {
                if (ultimaVizita.compareTo(diagnostic.getDataDiagnostic()) < 0) {
                    ultimaVizita = diagnostic.getDataDiagnostic();
                }
            }
            LocalDate dateToCheck = convertToLocalDate(ultimaVizita);
            if (dateToCheck.isAfter(startDate.minusDays(1)) && dateToCheck.isBefore(endDate.plusDays(1)))
                for (CategorieAnimal categorie : categorii) {
                    if (animal.getRasa().getCategorieAnimal().getId() == categorie.getId()) {
                        if (mapareaAparitiilor.keySet().contains(categorie.getNumeCategorie())) {
                            mapareaAparitiilor.put(categorie.getNumeCategorie(),
                                    mapareaAparitiilor.get(categorie.getNumeCategorie()) + 1);
                        } else {
                            mapareaAparitiilor.put(categorie.getNumeCategorie(), 1);
                        }
                        break;
                    }
                }
        }
        return mapareaAparitiilor;
    }

    public HashMap<String, Integer> mapByVarsta(LocalDate startDate, LocalDate endDate) {
        List<Animal> animale = animalService.getAllAnimals();
        HashMap<String, Integer> mapareaAparitiilor = new HashMap<>();

        for (Animal animal : animale) {

            if (animal.getDiagnostice().size() < 1)
                continue;
            Date ultimaVizita = animal.getDiagnostice().get(0).getDataDiagnostic();
            for (Diagnostic diagnostic : animal.getDiagnostice()) {
                if (ultimaVizita.compareTo(diagnostic.getDataDiagnostic()) < 0) {
                    ultimaVizita = diagnostic.getDataDiagnostic();
                }
            }
            LocalDate dateToCheck = convertToLocalDate(ultimaVizita);
            if (dateToCheck.isAfter(startDate.minusDays(1)) && dateToCheck.isBefore(endDate.plusDays(1))) {
                int varstaAnimal = animal.getVarsta();
                if (mapareaAparitiilor.keySet().contains(varstaAnimal + "")) {
                    mapareaAparitiilor.put(varstaAnimal + "", mapareaAparitiilor.get(varstaAnimal + "") + 1);
                } else {
                    mapareaAparitiilor.put(varstaAnimal + "", 1);
                }
            }
        }
        return mapareaAparitiilor;
    }

    public HashMap<String, HashMap<String, Integer>> mapByRase(LocalDate startDate, LocalDate endDate) {
        List<Animal> animale = animalService.getAllAnimalsBetweenDates(startDate, endDate);
        List<CategorieAnimal> categorii = categorieAnimalService.getAllCategories();
        List<RasaAnimal> rase = rasaAnimalService.getAllRase();
        HashMap<String, HashMap<String, Integer>> mapareaAparitiilorRaselorPeCategorii = new HashMap<>();
        HashMap<RasaAnimal, Integer> mapareaAparitiilorRaselor = new HashMap<>();

        for (Animal animal : animale) {
            for (RasaAnimal rasa : rase) {

                if (animal.getRasa().getId() != rasa.getId())
                    continue;

                if (mapareaAparitiilorRaselor.keySet().contains(rasa)) {
                    mapareaAparitiilorRaselor.put(rasa,
                            mapareaAparitiilorRaselor.get(rasa) + 1);
                } else {
                    mapareaAparitiilorRaselor.put(rasa, 1);
                }
            }
        }

        for (

        RasaAnimal rasa : mapareaAparitiilorRaselor.keySet()) {
            for (CategorieAnimal categorie : categorii) {
                if (categorie.getId() != rasa.getCategorieAnimal().getId())
                    continue;

                HashMap<String, Integer> aparitiiRase = new HashMap<>();

                if (mapareaAparitiilorRaselorPeCategorii.containsKey(categorie.getNumeCategorie())) {
                    aparitiiRase = mapareaAparitiilorRaselorPeCategorii.get(categorie.getNumeCategorie());
                }

                aparitiiRase.put(rasa.getNumeRasa(), mapareaAparitiilorRaselor.get(rasa));
                mapareaAparitiilorRaselorPeCategorii.put(categorie.getNumeCategorie(), aparitiiRase);
            }
        }

        return mapareaAparitiilorRaselorPeCategorii;
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public StatisticContainer StatisticRequest(String categorie,List<String> raseReq, List<String> list,
            List<String> list2, List<String> list3, List<String> specializariReq,
            List<String> mediciReq,LocalDate startDate, LocalDate endDate, String dateLocation) {

        Set<Long> rase = new HashSet<>();
        Set<Long> specii = new HashSet<>();
        Set<Long> diagnostice = new HashSet<>();
        Set<Long> medicamente = new HashSet<>();
        Set<Long> specializari = new HashSet<>();
        Set<Long> medici = new HashSet<>();

        try {
            raseReq.forEach(rasa -> {
                rase.add(Long.parseLong(rasa));
            });
        } catch (NumberFormatException e) {
        }

        try {
            list.forEach(specie -> {
                specii.add(Long.parseLong(specie));
            });
        } catch (NumberFormatException e) {
        }

        try {
            list2.forEach(diagnostic -> {
                diagnostice.add(Long.parseLong(diagnostic));
            });
        } catch (NumberFormatException e) {
        }

        try {
            list3.forEach(medicament -> {
                medicamente.add(Long.parseLong(medicament));
            });
        } catch (NumberFormatException e) {
        }

        try {
            list2.forEach(diagnostic -> {
                diagnostice.add(Long.parseLong(diagnostic));
            });
        } catch (NumberFormatException e) {
        }

        try {
            specializariReq.forEach(specializare -> {
                specializari.add(Long.parseLong(specializare));
            });
        } catch (NumberFormatException e) {
        }

        try {
            mediciReq.forEach(medic -> {
                medici.add(Long.parseLong(medic));
            });
        } catch (NumberFormatException e) {
        }

        return animalService.getStatistics(categorie, rase, specii, diagnostice, medicamente, specializari, medici,convertToDate(startDate), convertToDate(endDate), dateLocation);
    }

}
