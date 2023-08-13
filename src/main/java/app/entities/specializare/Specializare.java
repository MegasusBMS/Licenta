package app.entities.specializare;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entities.medic.Medic;
import app.entities.specializare.dt.SpecializareTable;
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
public class Specializare {
    
    @SequenceGenerator(
        name = "spcializare_sequence",
        sequenceName = "specializare_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "specializare_sequence"
    )
    @Id
    private long id;
    private String numeSpecializare;
    @OneToMany(mappedBy = "specializare")
    private List<Medic> medici;

    public Specializare(String numeSpecializare){
        this.numeSpecializare=numeSpecializare;
    }

    public SpecializareTable toSpecializareTable(){
        return new SpecializareTable(id,numeSpecializare);
    }

}
