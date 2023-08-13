package app.entities.medicament;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entities.categorieMedicament.CategorieMedicament;
import app.entities.medicament.dt.MedicamentTable;
import app.entities.medicamentTratament.MedicamentTratament;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Medicament {
    
    public Medicament(String numeMedicament, CategorieMedicament categorieMedicament, double pret) {
        this.nume = numeMedicament;
        this.categorieMedicament = categorieMedicament;
        this.pret=pret;
    }
    @SequenceGenerator(
        name = "medicament_sequence",
        sequenceName = "medicament_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "medicament_sequence"
    )
    @Id
    private  long id;
    private String nume;
    private double pret;
    @ManyToOne
    private CategorieMedicament categorieMedicament;
    @OneToMany(mappedBy = "medicament")
    private List<MedicamentTratament> tratamente;

    public MedicamentTable toMedicamentTable(){
        return new MedicamentTable(id,nume,categorieMedicament.getNumeCategorieMedicament(),pret);
    }
}
