package app.entities.animal;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import app.entities.animal.dt.AnimalTable;
import app.entities.diagnostic.Diagnostic;
import app.entities.rasa.RasaAnimal;
import app.entities.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Entity
@Table
@NoArgsConstructor
@Getter
public class Animal {

    @Id
    @SequenceGenerator(name = "animal_sequence", sequenceName = "animal_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_sequence")
    private long id;
    private String numeAnimal;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNastere;
    @ManyToOne
    private User stapan;
    @ManyToOne
    @Setter
    private RasaAnimal rasa;
    @OneToMany(mappedBy = "animal", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @OrderBy("dataDiagnostic DESC")
    private List<Diagnostic> diagnostice;

    public AnimalTable toAnimalTable() {
        String data = new SimpleDateFormat("yyyy-MM-dd").format(dataNastere);
        return new AnimalTable(id, numeAnimal, stapan.getUserName(), getVarsta(), data,
                rasa.getCategorieAnimal().getNumeCategorie(), rasa.getNumeRasa());
    }

    public int getVarsta() {
        LocalDate dn = dataNastere.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate azi = LocalDate.now();
        int varsta = Period.between(dn, azi).getYears();
        return varsta;
    }

    public Animal(String numeAnimal) {
        this.numeAnimal = numeAnimal;
    }

    public Animal(long id, String numeAnimal, Date dataNastere, User user, RasaAnimal rasaAnimal) {
        this.numeAnimal = numeAnimal;
        this.dataNastere = dataNastere;
        this.stapan = user;
        this.rasa = rasaAnimal;
        this.id = id;
    }

    public Animal(String numeAnimal, Date dataNastere, User user, RasaAnimal rasaAnimal) {
        this.numeAnimal = numeAnimal;
        this.dataNastere = dataNastere;
        this.stapan = user;
        this.rasa = rasaAnimal;
    }
}
