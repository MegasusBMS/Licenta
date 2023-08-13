package app.entities.categorieMedicament;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entities.categorieMedicament.dt.CategorieMedicamentTable;
import app.entities.medicament.Medicament;
import jakarta.persistence.Entity;
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
@Table
@NoArgsConstructor
@Getter
public class CategorieMedicament {
    @Id
    @SequenceGenerator(
        name = "categorie_medicament_sequence",
        sequenceName = "categorie_medicament_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "categorie_medicament_sequence"
    )
    private long id;
    private String numeCategorieMedicament;
    @OneToMany(mappedBy = "categorieMedicament")
    List<Medicament> medicamente;

    public CategorieMedicament(String numeCategorieMedicament){
        this.numeCategorieMedicament = numeCategorieMedicament;
    }

    public CategorieMedicamentTable toCategorieAnimalTable(){
        return new CategorieMedicamentTable(id, numeCategorieMedicament);
    }
}
