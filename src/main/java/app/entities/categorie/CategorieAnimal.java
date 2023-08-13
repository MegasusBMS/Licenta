package app.entities.categorie;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entities.categorie.dt.CategorieAnimalTable;
import app.entities.rasa.RasaAnimal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(name = "categorie_animal")
@NoArgsConstructor
@Getter
public class CategorieAnimal {
        
    @Id
    @SequenceGenerator(
        name = "categorie_animal_sequence",
        sequenceName = "categorie_animal_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "categorie_animal_sequence"
    )
    private long id;
    private String numeCategorie;
    @OneToMany(mappedBy = "categorieAnimal", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<RasaAnimal> rase;

    public CategorieAnimal(String numeCategorie){
        this.numeCategorie=numeCategorie;
    }

    public CategorieAnimalTable toCategorieAnimalTable(){
        return new CategorieAnimalTable(id,numeCategorie);
    }
}
