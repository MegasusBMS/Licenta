package app.entities.medic;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entities.medic.dt.MedicTable;
import app.entities.specializare.Specializare;
import app.entities.tratament.Tratament;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Medic {

    @SequenceGenerator(
        name = "medic_sequence",
        sequenceName = "medic_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "medic_sequence"
    )
    @Id
    private  long id;
    @Column(unique = true)
    private String fullName;
    @Column(unique = true)
    private long idParafa;
    private String password;
    @ManyToOne
    private Specializare specializare;
    @OneToMany(mappedBy = "medic", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Tratament> tratamente;

    public Medic(long idParafa, String password) {
        this.idParafa = idParafa;
        this.password = password;
    }

    public Medic(String fullName,long idParafa, String password,Specializare specializare) {
        this.idParafa = idParafa;
        this.password = password;
        this.fullName = fullName;
        this.specializare = specializare;
    }

    public Medic(String fullName){
        this.fullName = fullName;
    }

    public MedicTable toMedicTable(){
        return new MedicTable(id,fullName, idParafa, specializare.getNumeSpecializare());
    }

    public Medic(long id, String fullName, long idParafa, String password, Specializare specializare) {
        this.id=id;
        this.idParafa = idParafa;
        this.password = password;
        this.fullName = fullName;
        this.specializare = specializare;
    }
}
