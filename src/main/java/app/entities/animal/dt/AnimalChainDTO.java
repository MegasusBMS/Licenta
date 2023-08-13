package app.entities.animal.dt;

import app.entities.categorie.dt.CategorieAnimalDTO;
import app.entities.rasa.dt.RasaAnimalDTO;

public record AnimalChainDTO (     long id,String numeAnimal, int varsta, CategorieAnimalDTO categorieAnimal,
    RasaAnimalDTO rasa) {}
