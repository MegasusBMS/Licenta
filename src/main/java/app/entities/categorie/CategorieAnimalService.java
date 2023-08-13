package app.entities.categorie;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CategorieAnimalService {
    
    CategorieAnimalRepository categorieAnimalRepo;

    CategorieAnimalService(CategorieAnimalRepository categorieAnimalRepo){
        this.categorieAnimalRepo = categorieAnimalRepo;
    }

    public Page<CategorieAnimal> getCategorieAnimalPage(int page){
        return categorieAnimalRepo.findAll(PageRequest.of(page, 10));
    }

    public void createCategorieIfNotExist(CategorieAnimal categorie) {
        if(getCategorieAnimalByName(categorie).isPresent())
            return;
        categorieAnimalRepo.save(categorie);
    }

    public Optional<CategorieAnimal> getCategorieAnimalByName(CategorieAnimal categorie) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll() .withIgnorePaths("id");
        Example<CategorieAnimal> example = Example.of(categorie,matcher);
    
        return categorieAnimalRepo.findOne(example);
    }

    public Optional<CategorieAnimal> getCategorieAnimalByName(String numeCategorie) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll() .withIgnorePaths("id");
        Example<CategorieAnimal> example = Example.of(new CategorieAnimal(numeCategorie),matcher);
    
        return categorieAnimalRepo.findOne(example);
    }

    public Optional<CategorieAnimal> getCategorieAnimalById(Long id) {
        return Optional.of(categorieAnimalRepo.getReferenceById(id));
    }

    public List<CategorieAnimal> getAllCategories(){
        return categorieAnimalRepo.findAll();
    }
}
