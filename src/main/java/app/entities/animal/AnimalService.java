package app.entities.animal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import app.entities.animal.dt.AnimalDTO;
import app.entities.animal.dt.AnimalInput;
import app.entities.categorie.CategorieAnimalService;
import app.entities.rasa.RasaAnimal;
import app.entities.rasa.RasaAnimalService;
import app.entities.user.User;
import app.entities.user.UserService;
import app.statistics.StatisticContainer;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AnimalService {

    AnimalRepository animalRepo;
    RasaAnimalService rasaAnimalService;
    CategorieAnimalService categorieAnimalService;
    UserService userService;

    public List<AnimalDTO> getAnimalsOfUserAsDTO(User user) {

        List<AnimalDTO> animals = new ArrayList<>();
        user.getAnimale()
                .forEach(animal -> animals.add(new AnimalDTO(animal.getId(), animal.getNumeAnimal())));
        return animals;
    }

    public Page<Animal> getAnimalPage(int page){
        return animalRepo.findAll(PageRequest.of(page, 10));
    }

    public void createAnimalIfNotExist(AnimalInput animal) {
        Optional<User> user = userService.getUserByCNP(Long.parseLong(animal.cnp()));
        if(user.isEmpty())
            return;

        Optional<RasaAnimal> rasaAnimal = rasaAnimalService.getRasaAnimalById(Long.parseLong(animal.idRasa()));
        if(rasaAnimal.isEmpty())
            return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Animal animalAsAnimal;

        try {
            animalAsAnimal = new Animal(animal.numeAnimal(),dateFormat.parse(animal.dataNastere()),user.get(),rasaAnimal.get());
            
            animalRepo.save(animalAsAnimal);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void createAnimalIfNotExist(AnimalInput animal,long idRasa) {
        Optional<User> user = userService.getUserByCNP(Long.parseLong(animal.cnp()));
        if(user.isEmpty())
            return;

        Optional<RasaAnimal> rasaAnimal = rasaAnimalService.getRasaAnimalById(idRasa);
        if(rasaAnimal.isEmpty())
            return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Animal animalAsAnimal;
        try {
            animalAsAnimal = new Animal(animal.numeAnimal(),dateFormat.parse(animal.dataNastere()),user.get(),rasaAnimal.get());

            animalRepo.save(animalAsAnimal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean exist(Animal animal) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll() .withIgnorePaths("id");
        Example<Animal> example = Example.of(animal,matcher);
    
        return animalRepo.findOne(example).isPresent();
    }

    public Optional<Animal> getAnimalByName(String numeAnimal){
        ExampleMatcher matcher = ExampleMatcher.matchingAll() .withIgnorePaths("id", "varsta", "categorieAnimal_id","rasa_id", "stapan_UUID");
        Example<Animal> example = Example.of(new Animal(numeAnimal),matcher);
    
        return animalRepo.findOne(example);
    }
    
    public Optional<Animal> getAnimalById(Long id){
        return Optional.of(animalRepo.getReferenceById(id));
    }

    public List<Animal> getAllAnimals(){
        return animalRepo.findAll();
    }

    public List<Animal> getAllAnimalsBetweenDates(Date min,Date max){

        return animalRepo.findAllByDiagnosticeDataDiagnosticBetween(min, max);
    }

    public List<Animal> getAllAnimalsBetweenDates(LocalDate min,LocalDate max){

        return animalRepo.findAllByDiagnosticeDataDiagnosticBetween(convertLocalDateToDate(min), convertLocalDateToDate(max));
    }

    private Date convertLocalDateToDate(LocalDate date){
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public Page<Animal> getAllAnimalsBySearch(String search,String searchBy,int page){

        switch(searchBy){
            case "byCNP":
                return animalRepo.findAllByStapanCnpEquals(Long.parseLong(search),PageRequest.of(page, 20));
            case "byUserName":
                return animalRepo.findAllByStapanUserNameContains(search,PageRequest.of(page, 20));
            default:
                return animalRepo.findAllByNumeAnimalContains(search,PageRequest.of(page, 20));
        }

    }

    public StatisticContainer getStatistics(String categorie,Set<Long> rase,Set<Long> specii,Set<Long> diagnostice, Set<Long> medicamente, Set<Long> specificatie,Set<Long> medic,Date startDate,Date endDate,String dateLocation) {
        return animalRepo.groupByPropertyWithConditionsAsStatisticGrups(categorie, rase, specii, diagnostice, medicamente, specificatie, medic,startDate,endDate,dateLocation);
    }

    public void update(AnimalInput animal,long idRasa, long idAnimal) {

        Optional<User> user = userService.getUserByCNP(Long.parseLong(animal.cnp()));
        if(user.isEmpty())
            return;

        Optional<RasaAnimal> rasaAnimal = rasaAnimalService.getRasaAnimalById(idRasa);
        if(rasaAnimal.isEmpty())
            return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Animal animalAsAnimal;
        try {
            animalAsAnimal = new Animal(idAnimal,animal.numeAnimal(),dateFormat.parse(animal.dataNastere()),user.get(),rasaAnimal.get());

            animalRepo.update(animalAsAnimal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void update(AnimalInput animal, long animalId) {

        Optional<User> user = userService.getUserByCNP(Long.parseLong(animal.cnp()));
        if(user.isEmpty())
            return;

        Optional<RasaAnimal> rasaAnimal = rasaAnimalService.getRasaAnimalById(Long.parseLong(animal.idRasa()));
        if(rasaAnimal.isEmpty())
            return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Animal animalAsAnimal;

        try {
            animalAsAnimal = new Animal(animalId,animal.numeAnimal(),dateFormat.parse(animal.dataNastere()),user.get(),rasaAnimal.get());
            
            animalRepo.update(animalAsAnimal);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}
