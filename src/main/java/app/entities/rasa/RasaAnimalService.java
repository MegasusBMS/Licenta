package app.entities.rasa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import app.entities.categorie.CategorieAnimal;
import app.entities.categorie.CategorieAnimalService;
import app.entities.rasa.dt.RasaAnimalInput;

@Service
public class RasaAnimalService {
    RasaAnimalRepository rasaAnimalRepo;
    CategorieAnimalService categorieAnimalService;

    RasaAnimalService(RasaAnimalRepository rasaAnimalRepo, CategorieAnimalService categorieAnimalService){
        this.rasaAnimalRepo = rasaAnimalRepo;
        this.categorieAnimalService = categorieAnimalService;
    }

    public void createRasaIfNotExist(RasaAnimalInput rasa) {
        Optional<CategorieAnimal> categorie = categorieAnimalService.getCategorieAnimalById(Long.parseLong(rasa.idCategorie()));
        if(categorie.isEmpty())
        return;
        if(getRasaAnimalByName(rasa.numeRasa()).isPresent())
        return;
        rasaAnimalRepo.save(new RasaAnimal(rasa.numeRasa(),categorie.get()));
    }

    public Optional<RasaAnimal> getRasaAnimalByName(RasaAnimal rasa) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll() .withIgnorePaths("id","categorieAnimal");
        Example<RasaAnimal> example = Example.of(rasa,matcher);
    
        return rasaAnimalRepo.findOne(example);
    }

    public Optional<RasaAnimal> getRasaAnimalByName(String numeRasa) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll() .withIgnorePaths("id","categorieAnimal");
        Example<RasaAnimal> example = Example.of(new RasaAnimal(numeRasa),matcher);
    
        return rasaAnimalRepo.findOne(example);
    }

    public Page<RasaAnimal> getRasaAnimalPage(int page){
        return rasaAnimalRepo.findAll(PageRequest.of(page, 10));
    }

    public Optional<RasaAnimal> getRasaAnimalById(Long id) {
        return Optional.of(rasaAnimalRepo.getReferenceById(id));
    }
    public List<RasaAnimal> getAllRase(){
        return rasaAnimalRepo.findAll();
    }
}
