package app.entities.rasa;

import org.springframework.stereotype.Component;

import app.entities.categorie.CategorieAnimal;
import app.entities.rasa.dt.RasaAnimalTable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(name = "rasa_animal")
@NoArgsConstructor
@Getter
public class RasaAnimal {
        
    public RasaAnimal(String numeRasa) {
        this.numeRasa = numeRasa;
    }
    public RasaAnimal(String numeRasa, CategorieAnimal categorieAnima) {
        this.numeRasa = numeRasa;
        this.categorieAnimal = categorieAnima;
    }
    @SequenceGenerator(
        name = "rasa_animal_sequence",
        sequenceName = "rasa_animal_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "rasa_animal_sequence"
    )
    @Id
    private  long id;
    private String numeRasa;
    @ManyToOne
    private CategorieAnimal categorieAnimal;

    public RasaAnimalTable toRasaAnimalTable(){
        return new RasaAnimalTable(id,numeRasa, categorieAnimal.getNumeCategorie(),categorieAnimal.getId());
    }
}
