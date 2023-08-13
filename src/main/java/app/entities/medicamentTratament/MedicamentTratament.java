package app.entities.medicamentTratament;

import org.springframework.stereotype.Component;

import app.entities.medicament.Medicament;
import app.entities.tratament.Tratament;
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
@Table
@NoArgsConstructor
@Getter
public class MedicamentTratament {
    @SequenceGenerator(
        name = "medicament_tratament_sequence",
        sequenceName = "medicament_tratament_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "medicament_tratament_sequence"
    )
    @Id
    private long id;
    @ManyToOne
    private Tratament tratament;
    @ManyToOne
    private Medicament medicament;

    public MedicamentTratament(Tratament tratament, Medicament medicament){
        this.tratament = tratament;
        this.medicament = medicament;
    }
}
